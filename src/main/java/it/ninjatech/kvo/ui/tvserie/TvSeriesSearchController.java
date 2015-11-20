package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.tvserie.TvSerieManager;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;

import java.util.Map;

public class TvSeriesSearchController extends AbstractTvSerieSearchController<TvSeriesPathEntity, Map<TvSeriePathEntity, Boolean>> {

    private final TvSeriesPathEntity entity;
    
    public TvSeriesSearchController(TvSeriesPathEntity entity) {
        super();
        
        this.entity = entity;
        
        for (TvSeriePathEntity tvSeriePathEntity : this.entity.getTvSeries()) {
            if (tvSeriePathEntity.getTvSerie() == null) {
                this.entityMap.put(tvSeriePathEntity.getId(), tvSeriePathEntity);
            }
        }
    }

    @Override
    public Map<TvSeriePathEntity, Boolean> search() {
        Map<TvSeriePathEntity, Boolean> result = null;
        
        startSearch();
        
        result = TvSerieManager.getInstance().fetch(this.entity);
        
        return result;
    }

}
