package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.TvSerieCacheRemoteImageAsyncJob;
import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSerieImage;
import it.ninjatech.kvo.model.TvSerieImageProvider;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.worker.CachedFanartCopyWorker;
import it.ninjatech.kvo.worker.CachedImageFullWorker;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

public class TvSerieFanartChoiceController implements AsyncJobListener {

	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieFanart fanart;
	private final TvSerieFanartChoiceDialog view;
	private final Set<String> fanartJobIds;

	public TvSerieFanartChoiceController(TvSeriePathEntity tvSeriePathEntity, TvSerieFanart fanart) {
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.fanart = fanart;
		this.view = new TvSerieFanartChoiceDialog(this, this.tvSeriePathEntity, this.fanart);
		this.fanartJobIds = new HashSet<>();
	}

	@Override
	public void notify(String id, AsyncJob job) {
		if (job.getException() != null) {
//			UI.get().notifyException(job.getException());
			job.getException().printStackTrace();
		}
		else {
			if (this.fanartJobIds.remove(id)) {
				this.view.setFanart(id, ((TvSerieCacheRemoteImageAsyncJob)job).getImage());
			}
		}
	}

	public TvSerieFanartChoiceDialog getView() {
		return this.view;
	}

	public void start() {
		Dimension imageSize = this.view.getImageSize();

		// Gallery
		for (TvSerieImage theTvDbFanart : TvSerieUtils.getTheTvDbFanarts(this.tvSeriePathEntity, this.fanart)) {
			String id = theTvDbFanart.getId();
			this.fanartJobIds.add(id);
			TvSerieCacheRemoteImageAsyncJob job = new TvSerieCacheRemoteImageAsyncJob(id, TvSerieImageProvider.TheTvDb, theTvDbFanart.getPath(), imageSize);
			AsyncManager.getInstance().submit(id, job, this);
		}
		for (TvSerieImage fanarttvFanart : TvSerieUtils.getFanarttvFanarts(this.tvSeriePathEntity, this.fanart)) {
			String id = fanarttvFanart.getId();
			this.fanartJobIds.add(id);
			TvSerieCacheRemoteImageAsyncJob job = new TvSerieCacheRemoteImageAsyncJob(id, TvSerieImageProvider.Fanarttv, fanarttvFanart.getPath(), imageSize);
			AsyncManager.getInstance().submit(id, job, this);
		}
	}
	
	protected void notifyClosing() {
		for (String remoteFanartJobId : this.fanartJobIds) {
			AsyncManager.getInstance().cancelTvSerieCacheRemoteImageAsyncJob(remoteFanartJobId);
		}
		this.fanartJobIds.clear();
	}
	
	protected void notifyFanartSingleClick(String name) {
		CachedFanartCopyWorker worker = new CachedFanartCopyWorker(name, this.tvSeriePathEntity.getPath(), this.fanart);
		IndeterminateProgressDialogWorker<Boolean> progressWorker = new IndeterminateProgressDialogWorker<>(worker, this.fanart.getName());
		
		progressWorker.start();
		try {
			progressWorker.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
		
		this.view.setVisible(false);
	}
	
	protected void notifyFanartDoubleClick(String name) {
		CachedImageFullWorker worker = new CachedImageFullWorker(name);
		UIUtils.showTvSerieFanartFull(worker, this.fanart);
	}

}
