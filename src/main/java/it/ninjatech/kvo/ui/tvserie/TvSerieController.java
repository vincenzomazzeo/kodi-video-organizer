package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.TvSerieLocalFanartAsyncJob;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.UI;

import java.awt.Dimension;

public class TvSerieController implements AsyncJobListener<TvSerieLocalFanartAsyncJob> {

	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieView view;
	
	public TvSerieController(TvSeriePathEntity tvSeriePathEntity) {
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.view = new TvSerieView(this, this.tvSeriePathEntity);
	}

	@Override
	public void notify(String id, TvSerieLocalFanartAsyncJob job) {
		if (job.getException() != null) {
			UI.get().notifyException(job.getException());
		}
		else {
			this.view.setFanart(job.getBanner(), job.getCharacter(), job.getClearart(), job.getFanart(), job.getLandscape(), job.getLogo(), job.getPoster());
		}
	}

	public TvSerieView getView() {
		return this.view;
	}

	protected void loadLocalFanart(Dimension bannerSize, Dimension characterSize, Dimension clearartSize, Dimension fanartSize, Dimension landscapeSize, Dimension logoSize, Dimension posterSize) {
		TvSerieLocalFanartAsyncJob job = new TvSerieLocalFanartAsyncJob(this.tvSeriePathEntity, bannerSize, characterSize, clearartSize, fanartSize, landscapeSize, logoSize, posterSize);
		AsyncManager.getInstance().submit(this.tvSeriePathEntity.getId(), job, this);
	}
	
}
