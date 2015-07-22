package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.TvSerieLocalSeasonImageAsyncJob;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.ui.UI;

public class TvSerieSeasonController implements AsyncJobListener {

	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieSeason season;
	private final TvSerieSeasonDialog view;
	
	public TvSerieSeasonController(TvSeriePathEntity tvSeriePathEntity, TvSerieSeason season) {
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.season = season;
		this.view = new TvSerieSeasonDialog(this, this.tvSeriePathEntity, this.season);
	}
	
	@Override
	public void notify(String id, AsyncJob job) {
		if (job.getException() != null) {
			UI.get().notifyException(job.getException());
		}
		else {
			if (job.getClass().equals(TvSerieLocalSeasonImageAsyncJob.class)) {
				this.view.setSeasonImage(((TvSerieLocalSeasonImageAsyncJob)job).getImage());
			}
		}
	}
	
	public TvSerieSeasonDialog getView() {
		return this.view;
	}
	
	public void start() {
		TvSerieLocalSeasonImageAsyncJob job = new TvSerieLocalSeasonImageAsyncJob(this.tvSeriePathEntity, this.season, this.view.getSeasonImageSize());
		AsyncManager.getInstance().submit(this.season.getId(), job, this);
	}
	
	protected void notifyClosing() {
		
	}
	
}
