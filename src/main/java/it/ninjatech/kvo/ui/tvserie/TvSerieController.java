package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.job.CacheRemoteImageAsyncJob;
import it.ninjatech.kvo.async.job.PersonAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalFanartAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalSeasonImageAsyncJob;
import it.ninjatech.kvo.model.AbstractTvSerieImage;
import it.ninjatech.kvo.model.TvSerieActor;
import it.ninjatech.kvo.model.TvSerieImage;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.ImageGallery;
import it.ninjatech.kvo.ui.progressdialogworker.DeterminateProgressDialogWorker;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.ui.tvserie.TvSerieFanartSlider.FanartType;
import it.ninjatech.kvo.ui.tvserie.TvSerieImageChoiceDialog.ImageChoiceController;
import it.ninjatech.kvo.ui.worker.ExtraFanartsGalleryCreator;
import it.ninjatech.kvo.util.MemoryUtils;
import it.ninjatech.kvo.worker.CachedFanartCopyWorker;
import it.ninjatech.kvo.worker.CachedImageFullWorker;
import it.ninjatech.kvo.worker.ImageFullWorker;

import java.awt.Dimension;
import java.util.Set;

import com.alee.laf.optionpane.WebOptionPane;

public class TvSerieController implements ImageChoiceController {

	private final TvSerieView view;
	private final TvSerieImageLoaderAsyncJobHandler mainJobHandler;
	private final TvSerieImageLoaderAsyncJobHandler fanartChoiceJobHandler;
	private TvSeriePathEntity tvSeriePathEntity;
	private FanartType workingFanart;

	public TvSerieController() {
		this.view = new TvSerieView(this);
		this.mainJobHandler = new TvSerieImageLoaderAsyncJobHandler();
		this.fanartChoiceJobHandler = new TvSerieImageLoaderAsyncJobHandler();
	}

	@Override
	public void notifyClosing(String id) {
		this.fanartChoiceJobHandler.cancelAll();
	}

	@Override
	public void notifyImageLeftClick(String id, AbstractTvSerieImage image) {
		CachedFanartCopyWorker worker = new CachedFanartCopyWorker(image.getId(), this.tvSeriePathEntity.getPath(), this.workingFanart.getFanart());
		IndeterminateProgressDialogWorker<Boolean> progressWorker = new IndeterminateProgressDialogWorker<>(worker, this.workingFanart.getFanart().getName());
		
		progressWorker.start();
		try {
			progressWorker.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
		
		TvSerieLocalFanartAsyncJob job = new TvSerieLocalFanartAsyncJob(this.tvSeriePathEntity, this.workingFanart.getFanart(), this.workingFanart.getSize());
		this.mainJobHandler.handle(job, this.view);
	}

	@Override
	public void notifyImageRightClick(String id, AbstractTvSerieImage image) {
		CachedImageFullWorker worker = new CachedImageFullWorker(image.getId());
		UIUtils.showFullImage(worker, image.getId(), image.getId());
	}

	public TvSerieView getView() {
		return this.view;
	}

	public void showTvSerie(TvSeriePathEntity tvSeriePathEntity) {
		this.mainJobHandler.cancelAll();
		
		this.view.getFanartSlider().dispose();
		this.view.getSeasonSlider().dispose();
		this.view.getActorSlider().dispose();
		
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.view.fill(tvSeriePathEntity);
		this.view.getSeasonSlider().fill(tvSeriePathEntity);
		this.view.getActorSlider().fill(tvSeriePathEntity);

		for (TvSerieFanartSlider.FanartType fanart : TvSerieFanartSlider.FanartType.values()) {
			TvSerieLocalFanartAsyncJob job = new TvSerieLocalFanartAsyncJob(tvSeriePathEntity, fanart.getFanart(), fanart.getSize());
			this.mainJobHandler.handle(job, this.view);
		}

		Dimension seasonSize = this.view.getSeasonSlider().getSeasonSize();
		for (TvSerieSeason season : tvSeriePathEntity.getTvSerie().getSeasons()) {
			TvSerieLocalSeasonImageAsyncJob job = new TvSerieLocalSeasonImageAsyncJob(this.tvSeriePathEntity, season, seasonSize);
			this.mainJobHandler.handle(job, this.view);
		}

		Dimension actorSize = this.view.getActorSlider().getActorSize();
		for (TvSerieActor actor : tvSeriePathEntity.getTvSerie().getActors()) {
			PersonAsyncJob job = new PersonAsyncJob(actor.getId(), actor.getName(), actorSize);
			this.mainJobHandler.handle(job, this.view, actor);
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

	protected void notifyFanartLeftClick(FanartType fanart) {
		if (TvSerieUtils.hasFanarts(this.tvSeriePathEntity, fanart.getFanart())) {
			this.workingFanart = fanart;
			Set<TvSerieImage> images = TvSerieUtils.getFanarts(this.tvSeriePathEntity, fanart.getFanart());
			Dimension imageSize = Dimensions.getTvSerieFanartChooserSize(fanart.getFanart());
			TvSerieImageChoiceDialog<TvSerieImage> dialog = new TvSerieImageChoiceDialog<>("", this, String.format("%s - %s", TvSerieUtils.getTitle(this.tvSeriePathEntity), fanart.getFanart().getName()), images, imageSize);
			for (TvSerieImage image : images) {
				CacheRemoteImageAsyncJob job = new CacheRemoteImageAsyncJob(image.getId(), image.getProvider(), image.getPath(), imageSize);
				this.fanartChoiceJobHandler.handle(job, dialog);
			}
			dialog.setVisible(true);
			this.workingFanart = null;
		}
		else {
			WebOptionPane.showMessageDialog(UI.get(), String.format("No %s fanart found. Try to refresh TV Serie.", fanart.getFanart().getName()));
		}
	}

	protected void notifyFanartRightClick(FanartType fanart) {
		ImageFullWorker worker = new ImageFullWorker(this.tvSeriePathEntity.getPath(), fanart.getFanart().getFilename());
		UIUtils.showFullImage(worker, fanart.getFanart().getName(), fanart.getFanart().getName());
	}
	
	protected void notifySeasonLeftClick(TvSerieSeason season) {
		// TODO check if it's complete
		MemoryUtils.printMemory("Before Season - Controller");
		TvSerieSeasonController controller = new TvSerieSeasonController(this.tvSeriePathEntity, season);
		controller.start();
		MemoryUtils.printMemory("Before Season - Controller started");
		controller.getView().setVisible(true);
		MemoryUtils.printMemory("After Season");
	}
	
	protected void notifySeasonRightClick(TvSerieSeason season) {
		// TODO
	}

	protected void notifyActorRightClick(TvSerieActor actor) {
		UIUtils.showPersonFullImage(actor.getName());
	}

}
