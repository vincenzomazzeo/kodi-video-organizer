package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.tvserie.TvSerieManager;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TvSerieSearchController extends AbstractTvSerieSearchController<List<TvSeriePathEntity>, Map<TvSeriePathEntity, Boolean>> {

    public TvSerieSearchController(List<TvSeriePathEntity> entities) {
        super();

        for (TvSeriePathEntity entity : entities) {
            this.entityMap.put(entity.getId(), entity);
        }
    }

    @Override
    public Map<TvSeriePathEntity, Boolean> search() {
        Map<TvSeriePathEntity, Boolean> result = new HashMap<>();

        startSearch();

        List<TvSeriePathEntity> entities = new ArrayList<>(this.entityMap.values());

        if (!entities.isEmpty()) {
            List<Boolean> fetchResult = TvSerieManager.getInstance().fetch(entities);
            for (int i = 0, n = entities.size(); i < n; i++) {
                result.put(entities.get(i), fetchResult.get(i));
            }
        }

        return result;
    }

}
