package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.TvSerieCacheRemoteImageAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalFanartAsyncJob;
import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSerieImage;
import it.ninjatech.kvo.model.TvSerieImageProvider;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.worker.CachedImageFullWorker;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

public class TvSerieFanartChoiceController implements AsyncJobListener {

	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieFanart fanart;
	private final TvSerieFanartChoiceDialog view;
	private final Set<String> remoteFanartJobIds;
	private String localFanartJobId;

	public TvSerieFanartChoiceController(TvSeriePathEntity tvSeriePathEntity, TvSerieFanart fanart) {
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.fanart = fanart;
		this.view = new TvSerieFanartChoiceDialog(this, this.tvSeriePathEntity, this.fanart);
		this.remoteFanartJobIds = new HashSet<>();
	}

	@Override
	public void notify(String id, AsyncJob job) {
		if (job.getException() != null) {
			UI.get().notifyException(job.getException());
		}
		else {
			if (job.getClass().equals(TvSerieLocalFanartAsyncJob.class)) {
				if (this.localFanartJobId != null) {
					this.localFanartJobId = null;
					this.view.setCurrentFanart(((TvSerieLocalFanartAsyncJob)job).getImage());
				}
			}
			else if (job.getClass().equals(TvSerieCacheRemoteImageAsyncJob.class)) {
				if (this.remoteFanartJobIds.remove(id)) {
					this.view.setFanart(id, ((TvSerieCacheRemoteImageAsyncJob)job).getImage());
				}
			}
		}
	}

	public TvSerieFanartChoiceDialog getView() {
		return this.view;
	}

	public void start() {
		Dimension imageSize = this.view.getImageSize();
		// Local fanart
		this.localFanartJobId = this.tvSeriePathEntity.getId();
		TvSerieLocalFanartAsyncJob localFanartJob = new TvSerieLocalFanartAsyncJob(this.tvSeriePathEntity, this.fanart, imageSize);
		AsyncManager.getInstance().submit(this.localFanartJobId, localFanartJob, this);

		// Gallery
		for (TvSerieImage theTvDbFanart : TvSerieUtils.getTheTvDbFanarts(this.tvSeriePathEntity, this.fanart)) {
			String id = theTvDbFanart.getId();
			this.remoteFanartJobIds.add(id);
			TvSerieCacheRemoteImageAsyncJob job = new TvSerieCacheRemoteImageAsyncJob(id, TvSerieImageProvider.TheTvDb, theTvDbFanart.getPath(), imageSize);
			AsyncManager.getInstance().submit(id, job, this);
		}
		for (TvSerieImage fanarttvFanart : TvSerieUtils.getFanarttvFanarts(this.tvSeriePathEntity, this.fanart)) {
			String id = fanarttvFanart.getId();
			this.remoteFanartJobIds.add(id);
			TvSerieCacheRemoteImageAsyncJob job = new TvSerieCacheRemoteImageAsyncJob(id, TvSerieImageProvider.Fanarttv, fanarttvFanart.getPath(), imageSize);
			AsyncManager.getInstance().submit(id, job, this);
		}
	}
	
	protected void notifyClosing() {
		if (this.localFanartJobId != null) {
			AsyncManager.getInstance().cancelTvSerieLocalFanartAsyncJob(this.localFanartJobId);
		}
		for (String remoteFanartJobId : this.remoteFanartJobIds) {
			AsyncManager.getInstance().cancelTvSerieCacheRemoteImageAsyncJob(remoteFanartJobId);
		}
		this.remoteFanartJobIds.clear();
	}
	
	protected void notifyFanartDoubleClick(String name) {
		CachedImageFullWorker worker = new CachedImageFullWorker(name);
		UIUtils.showTvSerieFanartFull(worker, this.fanart);
	}

}
