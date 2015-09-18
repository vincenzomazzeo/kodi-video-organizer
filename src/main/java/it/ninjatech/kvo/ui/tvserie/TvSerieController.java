package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.job.CacheRemoteImageAsyncJob;
import it.ninjatech.kvo.async.job.PersonAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalFanartAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalSeasonImageAsyncJob;
import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.model.AbstractTvSerieImage;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSerieActor;
import it.ninjatech.kvo.tvserie.model.TvSerieFanart;
import it.ninjatech.kvo.tvserie.model.TvSerieImage;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.AbstractImageSlider.ImageSliderListener;
import it.ninjatech.kvo.ui.component.ImageGallery;
import it.ninjatech.kvo.ui.component.MessageDialog;
import it.ninjatech.kvo.ui.progressdialogworker.DeterminateProgressDialogWorker;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.ui.tvserie.TvSerieImageChoiceDialog.ImageChoiceController;
import it.ninjatech.kvo.ui.worker.ExtraFanartsGalleryCreator;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.MemoryUtils;
import it.ninjatech.kvo.worker.CachedFanartCopyWorker;
import it.ninjatech.kvo.worker.CachedImageFullWorker;
import it.ninjatech.kvo.worker.ImageFullWorker;
import it.ninjatech.kvo.worker.TvSerieSeasonCreator;

import java.awt.Dimension;
import java.util.Set;

import com.alee.laf.optionpane.WebOptionPane;

public class TvSerieController implements ImageChoiceController, ImageSliderListener {

	protected static final String ACTORS_SLIDER_ID = "Actors";
	protected static final String FANARTS_SLIDER_ID = "Fanarts";
	protected static final String SEASONS_SLIDER_ID = "Seasons";

	private final TvSerieView view;
	private final TvSerieImageLoaderAsyncJobHandler mainJobHandler;
	private final TvSerieImageLoaderAsyncJobHandler fanartChoiceJobHandler;
	private TvSerie tvSerie;
	private TvSerieFanart workingFanart;

	public TvSerieController() {
		this.view = new TvSerieView(this);
		this.mainJobHandler = new TvSerieImageLoaderAsyncJobHandler();
		this.fanartChoiceJobHandler = new TvSerieImageLoaderAsyncJobHandler();
	}

	@Override
	public void notifyImageChoiceClosing() {
		this.fanartChoiceJobHandler.cancelAll();
	}

	@Override
	public void notifyImageChoiceLeftClick(AbstractTvSerieImage image) {
		CachedFanartCopyWorker worker = new CachedFanartCopyWorker(image.getId(), this.tvSerie.getTvSeriePathEntity().getPath(), this.workingFanart);
		IndeterminateProgressDialogWorker.show(worker, this.workingFanart.getName());

		TvSerieLocalFanartAsyncJob job = new TvSerieLocalFanartAsyncJob(this.tvSerie, this.workingFanart, this.view.getFanartSize(this.workingFanart));
		this.mainJobHandler.handle(job, this.view);
	}

	@Override
	public void notifyImageChoiceRightClick(AbstractTvSerieImage image) {
		CachedImageFullWorker worker = new CachedImageFullWorker(image.getId());
		UIUtils.showFullImage(worker, image.getId(), image.getId());
	}

	@Override
	public void notifyImageSliderLeftClick(String id, Object data) {
		if (id.equals(FANARTS_SLIDER_ID)) {
			TvSerieFanart fanart = (TvSerieFanart)data;
			if (TvSerieHelper.hasFanarts(this.tvSerie, fanart)) {
				this.workingFanart = fanart;
				Set<TvSerieImage> images = TvSerieHelper.getFanarts(this.tvSerie, fanart);
				Dimension imageSize = Dimensions.getTvSerieFanartChooserSize(fanart);
				TvSerieImageChoiceDialog dialog = TvSerieImageChoiceDialog.getInstance(this, String.format("%s - %s", TvSerieHelper.getTitle(this.tvSerie.getTvSeriePathEntity()), fanart.getName()), images, imageSize);
				for (TvSerieImage image : images) {
					CacheRemoteImageAsyncJob job = new CacheRemoteImageAsyncJob(image.getId(), image.getProvider(), image.getPath(), imageSize);
					this.fanartChoiceJobHandler.handle(job, dialog);
				}
				dialog.setVisible(true);
				this.workingFanart = null;
			}
			else {
				WebOptionPane.showMessageDialog(UI.get(), Labels.getNoFanartTryRefresh(fanart.getName()));
			}
		}
		else if (id.equals(SEASONS_SLIDER_ID)) {
			TvSerieSeason season = (TvSerieSeason)data;
			if (TvSerieHelper.existsLocalSeason(season)) {
				MemoryUtils.printMemory("Before Season - Controller");
				TvSerieSeasonController controller = new TvSerieSeasonController(season);
				controller.start();
				MemoryUtils.printMemory("Before Season - Controller started");
				controller.getView().setVisible(true);
				MemoryUtils.printMemory("After Season");
				if (controller.isConfirmed()) {
					this.view.refreshSeason(season);
					TvSerieLocalSeasonImageAsyncJob job = new TvSerieLocalSeasonImageAsyncJob(season, this.view.getSeasonSize());
					this.mainJobHandler.handle(job, this.view);
				}
			}
			else {
				MessageDialog messageDialog = MessageDialog.getInstance(Labels.TV_SERIE_SEASON_CREATION, Labels.getTvSerieSeasonCreationMessage(season), MessageDialog.Type.Question);
				messageDialog.setVisible(true);
				if (messageDialog.isConfirmed()) {
					TvSerieSeasonCreator creator = new TvSerieSeasonCreator(season);
					Boolean result = IndeterminateProgressDialogWorker.show(creator, id);
					messageDialog = MessageDialog.getInstance(Labels.TV_SERIE_SEASON_CREATION,
															  result ? Labels.getTvSerieSeasonCreationSuccessMessage(season) : Labels.getTvSerieSeasonCreationFailMessage(season),
															  result ? MessageDialog.Type.Message : MessageDialog.Type.Error);
					messageDialog.setVisible(true);
					this.view.refreshSeason(season);
				}
			}
		}
	}

	@Override
	public void notifyImageSliderRightClick(String id, Object data) {
		if (id.equals(FANARTS_SLIDER_ID)) {
			TvSerieFanart fanart = (TvSerieFanart)data;
			ImageFullWorker worker = new ImageFullWorker(this.tvSerie.getTvSeriePathEntity().getPath(), fanart.getFilename());
			UIUtils.showFullImage(worker, fanart.getName(), fanart.getName());
		}
		else if (id.equals(SEASONS_SLIDER_ID)) {
			TvSerieSeason season = (TvSerieSeason)data;
			if (TvSerieHelper.existsLocalSeasonPoster(season)) {
				ImageFullWorker worker = new ImageFullWorker(this.tvSerie.getTvSeriePathEntity().getPath(), TvSerieHelper.getSeasonPosterFilename(season));
				UIUtils.showFullImage(worker, Labels.getTvSerieSeason(season), Labels.getTvSerieSeason(season));
			}
		}
		else if (id.equals(ACTORS_SLIDER_ID)) {
			UIUtils.showPersonFullImage(((TvSerieActor)data).getName());
		}
	}

	public TvSerieView getView() {
		return this.view;
	}

	public void showTvSerie(TvSerie tvSerie) {
		this.mainJobHandler.cancelAll();

		this.tvSerie = tvSerie;
		this.view.fill(this.tvSerie);

		for (TvSerieFanart fanart : TvSerieFanart.values()) {
			TvSerieLocalFanartAsyncJob job = new TvSerieLocalFanartAsyncJob(this.tvSerie, fanart, this.view.getFanartSize(fanart));
			this.mainJobHandler.handle(job, this.view);
		}

		// Seasons
		for (TvSerieSeason season : tvSerie.getSeasons()) {
			TvSerieLocalSeasonImageAsyncJob job = new TvSerieLocalSeasonImageAsyncJob(season, this.view.getSeasonSize());
			this.mainJobHandler.handle(job, this.view);
		}

		// Actors
		for (TvSerieActor actor : tvSerie.getActors()) {
			PersonAsyncJob job = new PersonAsyncJob(actor.getId(), actor.getName(), this.view.getActorSize());
			this.mainJobHandler.handle(job, this.view, actor);
		}
	}

	public void destroy() {
		this.mainJobHandler.cancelAll();
		this.fanartChoiceJobHandler.cancelAll();
		this.view.destroy();
	}

	protected void notifyExtraFanartsClick() {
		ExtraFanartsGalleryCreator creator = new ExtraFanartsGalleryCreator(TvSerieHelper.getExtrafanartPath(this.tvSerie.getTvSeriePathEntity()), TvSerieHelper.getTitle(this.tvSerie.getTvSeriePathEntity()), TvSerieHelper.getExtraFanarts(this.tvSerie.getTvSeriePathEntity()));
		ImageGallery result = DeterminateProgressDialogWorker.show(creator, TvSerieHelper.getTitle(this.tvSerie.getTvSeriePathEntity()));
		if (result != null) {
			result.setVisible(true);
			result.release();
		}
	}

}
