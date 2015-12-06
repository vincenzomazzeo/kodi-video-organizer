package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.tvserie.TvSerieManager;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class TvSerieFetchController {

    private final Map<String, TvSeriePathEntity> entityMap;
    private final TvSerieFetchDialog view;
    private EnhancedLocale language;

    public TvSerieFetchController(Set<TvSeriePathEntity> entities) {
        this.entityMap = new Hashtable<>();
        this.view = TvSerieFetchDialog.getInstance(this);

        for (TvSeriePathEntity entity : entities) {
            this.entityMap.put(entity.getId(), entity);
        }
    }

    public void setLanguage(EnhancedLocale language) {
        this.language = language;
    }

    public Set<TvSeriePathEntity> onlySearch() {
        Set<TvSeriePathEntity> result = new HashSet<>();

        List<SearchData> searchDatas = new ArrayList<>();
        EnhancedLocale language = this.language == null || EnhancedLocaleMap.isEmptyLocale(this.language)
                ? EnhancedLocaleMap.getByLanguage(SettingsHandler.getInstance().getSettings().getTheTvDbPreferredLanguage())
                : this.language;
        for (TvSeriePathEntity tvSeriePathEntity : this.entityMap.values()) {
            if (tvSeriePathEntity.getTvSerie() == null || StringUtils.isBlank(tvSeriePathEntity.getTvSerie().getProviderId())) {
                searchDatas.add(new SearchData(tvSeriePathEntity.getId(), tvSeriePathEntity.getLabel(), tvSeriePathEntity.getLabel(), language));
            }
        }

        if (!doSearch(searchDatas)) {
            this.view.setVisible(true);
            this.view.release();
        }

        result.addAll(this.entityMap.values());

        return result;
    }
    
    public Map<Boolean, Set<TvSeriePathEntity>> searchAndFetch() {
        Map<Boolean, Set<TvSeriePathEntity>> result = new HashMap<>();

        result.put(Boolean.TRUE, new HashSet<TvSeriePathEntity>());
        result.put(Boolean.FALSE, new HashSet<TvSeriePathEntity>());

        List<SearchData> searchDatas = new ArrayList<>();
        EnhancedLocale language = this.language == null || EnhancedLocaleMap.isEmptyLocale(this.language)
                ? EnhancedLocaleMap.getByLanguage(SettingsHandler.getInstance().getSettings().getTheTvDbPreferredLanguage())
                : this.language;
        for (TvSeriePathEntity tvSeriePathEntity : this.entityMap.values()) {
            if (tvSeriePathEntity.getTvSerie() == null || StringUtils.isBlank(tvSeriePathEntity.getTvSerie().getProviderId())) {
                searchDatas.add(new SearchData(tvSeriePathEntity.getId(), tvSeriePathEntity.getLabel(), tvSeriePathEntity.getLabel(), language));
            }
        }

        if (!doSearch(searchDatas)) {
            this.view.setVisible(true);
            this.view.release();
        }

        List<TvSeriePathEntity> entities = new ArrayList<>(this.entityMap.values());

        if (!entities.isEmpty()) {
            List<Boolean> fetchResult = TvSerieManager.getInstance().fetch(entities);
            for (int i = 0, n = entities.size(); i < n; i++) {
                Set<TvSeriePathEntity> tvSeriePathEntities = result.get(fetchResult.get(i));
                tvSeriePathEntities.add(entities.get(i));
            }
        }

        return result;
    }

    protected void notifyCancel() {
        this.entityMap.clear();
        this.view.setVisible(false);
        this.view.release();
    }

    protected void notifyConfirm(Map<String, Object> data) {
        List<SearchData> searchDatas = new ArrayList<>();
        Set<String> ignoredIds = new HashSet<>();
        for (String id : data.keySet()) {
            Object value = data.get(id);
            if (value != null) {
                if (value.getClass().equals(TvSerie.class)) {
                    this.entityMap.get(id).setTvSerie((TvSerie)value);
                }
                else {
                    // Search
                    String search = (String)((Object[])value)[0];
                    EnhancedLocale language = (EnhancedLocale)((Object[])value)[1];
                    searchDatas.add(new SearchData(id, this.entityMap.get(id).getLabel(), search, language));
                }
            }
            else {
                ignoredIds.add(id);
            }
        }

        if (searchDatas.isEmpty() || doSearch(searchDatas)) {
            for (String ignoredId : ignoredIds) {
                this.entityMap.remove(ignoredId);
            }
            this.view.setVisible(false);
            this.view.release();
        }
        else {
            this.view.refresh();
        }
    }

    private boolean doSearch(List<SearchData> searchDatas) {
        boolean result = true;

        Map<String, SearchData> searchDataMap = new HashMap<>();
        Map<String, EnhancedLocale> searchMap = new HashMap<>();
        for (SearchData searchData : searchDatas) {
            searchDataMap.put(searchData.search, searchData);
            searchMap.put(searchData.search, searchData.language);
        }

        Map<String, List<TvSerie>> searchResult = TvSerieManager.getInstance().search(searchMap);
        for (String searchKey : searchResult.keySet()) {
            SearchData searchData = searchDataMap.get(searchKey);
            List<TvSerie> tvSeries = searchResult.get(searchKey);
            if (tvSeries.size() == 1) {
                TvSeriePathEntity tvSeriePathEntity = this.entityMap.get(searchData.id);
                tvSeriePathEntity.setTvSerie(tvSeries.get(0));
            }
            else if (tvSeries.isEmpty()) {
                this.view.addFreeText(searchData.id, searchData.name);
                result = false;
            }
            else {
                List<TvSerie> candidates = new ArrayList<>();
                for (TvSerie tvSerie : tvSeries) {
                    if (searchData.language.equals(EnhancedLocaleMap.getEmptyLocale())
                        || searchData.language.equals(tvSerie.getLanguage())) {
                        candidates.add(tvSerie);
                    }
                }
                if (candidates.isEmpty()) {
                    this.view.remove(searchData.id, searchData.name);
                    this.view.addFreeText(searchData.id, searchData.name);
                    result = false;
                }
                else if (candidates.size() == 1) {
                    TvSeriePathEntity tvSeriePathEntity = this.entityMap.get(searchData.id);
                    tvSeriePathEntity.setTvSerie(candidates.get(0));
                }
                else {
                    this.view.remove(searchData.id, searchData.name);
                    this.view.addMultiResult(searchData.id, searchData.name, candidates);
                    result = false;
                }
            }
        }

        return result;
    }

    private static class SearchData {

        private final String id;
        private final String name;
        private final String search;
        private final EnhancedLocale language;

        private SearchData(String id, String name, String search, EnhancedLocale language) {
            this.id = id;
            this.name = name;
            this.search = search;
            this.language = language;
        }

    }

}
