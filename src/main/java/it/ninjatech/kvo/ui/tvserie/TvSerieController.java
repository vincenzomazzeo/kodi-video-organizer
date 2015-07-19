package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.TvSerieActorImageAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalFanartAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieSeasonImageAsyncJob;
import it.ninjatech.kvo.model.TvSerieActor;
import it.ninjatech.kvo.model.TvSerieImage;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.component.ActorFullImagePane;
import it.ninjatech.kvo.ui.component.FullImageDialog;
import it.ninjatech.kvo.ui.component.ImageGallery;
import it.ninjatech.kvo.ui.component.SimpleFullImagePane;
import it.ninjatech.kvo.ui.progressdialogworker.DeterminateProgressDialogWorker;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.ui.tvserie.TvSerieFanartSlider.FanartType;
import it.ninjatech.kvo.ui.worker.ExtraFanartsGalleryCreator;
import it.ninjatech.kvo.worker.ActorFullWorker;
import it.ninjatech.kvo.worker.ImageFullWorker;

import java.awt.Dimension;
import java.awt.Image;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.image.WebImage;
import com.alee.laf.optionpane.WebOptionPane;

public class TvSerieController implements AsyncJobListener {

	private final TvSerieView view;
	private TvSeriePathEntity tvSeriePathEntity;

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
			else if (job.getClass().equals(TvSerieSeasonImageAsyncJob.class)) {
				this.view.getSeasonSlider().setSeason(((TvSerieSeasonImageAsyncJob)job).getSeason(), ((TvSerieSeasonImageAsyncJob)job).getImage());
			}
			else if (job.getClass().equals(TvSerieActorImageAsyncJob.class)) {
				this.view.getActorSlider().setActor(((TvSerieActorImageAsyncJob)job).getActor(), ((TvSerieActorImageAsyncJob)job).getImage());
			}
		}
	}
	
	public TvSerieView getView() {
		return this.view;
	}

	public void showTvSerie(TvSeriePathEntity tvSeriePathEntity) {
		// TODO gestire activation/deactivation
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.view.fill(tvSeriePathEntity);
		this.view.getSeasonSlider().fill(tvSeriePathEntity);
		this.view.getActorSlider().fill(tvSeriePathEntity);

		for (TvSerieFanartSlider.FanartType fanart : TvSerieFanartSlider.FanartType.values()) {
			TvSerieLocalFanartAsyncJob job = new TvSerieLocalFanartAsyncJob(tvSeriePathEntity, fanart.getFanart(), fanart.getSize());
			AsyncManager.getInstance().submit(String.format("%s_%s", tvSeriePathEntity.getId(), fanart), job, this);
		}
		
		// TODO replace with local images
//		Dimension seasonSize = this.view.getSeasonSlider().getSeasonSize();
//		for (TvSerieSeason season : tvSeriePathEntity.getTvSerie().getSeasons()) {
//			TvSerieImage image = season.getImage();
//			if (image != null) {
//				TvSerieSeasonImageAsyncJob job = new TvSerieSeasonImageAsyncJob(season, seasonSize);
//				AsyncManager.getInstance().submit(season.getId(), job, this);
//			}
//		}

		Dimension actorSize = this.view.getActorSlider().getActorSize();
		for (TvSerieActor actor : tvSeriePathEntity.getTvSerie().getActors()) {
			TvSerieActorImageAsyncJob job = new TvSerieActorImageAsyncJob(actor, actorSize);
			AsyncManager.getInstance().submit(actor.getId(), job, this);
		}
	}
	
	protected void notifyExtraFanartsClick() {
		ExtraFanartsGalleryCreator creator = new ExtraFanartsGalleryCreator(TvSerieUtils.getTitle(this.tvSeriePathEntity), TvSerieUtils.getExtraFanarts(this.tvSeriePathEntity));
		DeterminateProgressDialogWorker<ImageGallery> progressWorker = new DeterminateProgressDialogWorker<>(creator, TvSerieUtils.getTitle(this.tvSeriePathEntity));
		
		ImageGallery result = null;
		
		progressWorker.start();
		try {
			result = progressWorker.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
		
		if (result != null) {
			result.setVisible(true);
		}
	}
	
	protected void notifyFanartDoubleClick(FanartType fanartType) {
		ImageFullWorker worker = new ImageFullWorker(tvSeriePathEntity.getPath(), fanartType.getFanart().getFilename());
		IndeterminateProgressDialogWorker<Image> progressWorker = new IndeterminateProgressDialogWorker<>(worker, fanartType.getFanart().getName());
		
		Image result = null;
		progressWorker.start();
		try {
			result = progressWorker.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
		
		if (result != null) {
			FullImageDialog dialog = new FullImageDialog(new SimpleFullImagePane(result), fanartType.getFanart().getName());
			dialog.setVisible(true);
		}
	}
	
	protected void notifyActorDoubleClick(TvSerieActor actor) {
		ActorFullWorker worker = new ActorFullWorker(actor.getId(), actor.getName());
		IndeterminateProgressDialogWorker<ActorFullWorker.ActorFullWorkerResult> progressWorker = new IndeterminateProgressDialogWorker<>(worker, actor.getName());
		
		ActorFullWorker.ActorFullWorkerResult result = null;
		
		progressWorker.start();
		try {
			result = progressWorker.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
		
		if (result != null && (result.getImage() != null || StringUtils.isNotBlank(result.getImdbId()))) {
			ActorFullImagePane pane = new ActorFullImagePane(result.getImage(), result.getImdbId());
			FullImageDialog dialog = new FullImageDialog(pane, actor.getName());
			dialog.setVisible(true);
		}
		else {
			WebOptionPane.showMessageDialog(UI.get(), "Neither image nor IMDB link available for this actor");
		}
	}

}
