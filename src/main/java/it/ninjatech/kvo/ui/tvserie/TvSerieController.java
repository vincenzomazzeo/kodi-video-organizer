package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.TvSerieActorsAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalFanartAsyncJob;
import it.ninjatech.kvo.model.TvSerieActor;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.UI;

import java.awt.Dimension;

public class TvSerieController implements AsyncJobListener {

	private final TvSerieView view;

	public TvSerieController() {
		this.view = new TvSerieView(this);
	}

	@Override
	public void notify(String id, AsyncJob job) {
		if (job.getException() != null) {
			UI.get().notifyException(job.getException());
		}
		else {
			if (job.getClass().equals(TvSerieLocalFanartAsyncJob.class)) {
				this.view.getFanartSlider().setFanart(((TvSerieLocalFanartAsyncJob)job).getFanart(), ((TvSerieLocalFanartAsyncJob)job).getImage());
			}
			else if (job.getClass().equals(TvSerieActorsAsyncJob.class)) {
				this.view.getActorSlider().setActor(((TvSerieActorsAsyncJob)job).getActor(), ((TvSerieActorsAsyncJob)job).getImage());
			}
		}
	}
	
	public TvSerieView getView() {
		return this.view;
	}

	public void showTvSerie(TvSeriePathEntity tvSeriePathEntity) {
		// TODO gestire activation/deactivation
		this.view.fill(tvSeriePathEntity);
		this.view.getSeasonSlider().fill(tvSeriePathEntity);
		this.view.getActorSlider().fill(tvSeriePathEntity);

		for (TvSerieFanartSlider.FanartType fanart : TvSerieFanartSlider.FanartType.values()) {
			TvSerieLocalFanartAsyncJob job = new TvSerieLocalFanartAsyncJob(tvSeriePathEntity, fanart.getFanart(), fanart.getSize());
			AsyncManager.getInstance().submit(String.format("%s_%s", tvSeriePathEntity.getId(), fanart), job, this);
		}

		Dimension actorSize = this.view.getActorSlider().getActorSize();
		for (TvSerieActor actor : tvSeriePathEntity.getTvSerie().getActors()) {
			TvSerieActorsAsyncJob job = new TvSerieActorsAsyncJob(actor, actorSize);
			AsyncManager.getInstance().submit(actor.getId(), job, this);
		}
	}

}
