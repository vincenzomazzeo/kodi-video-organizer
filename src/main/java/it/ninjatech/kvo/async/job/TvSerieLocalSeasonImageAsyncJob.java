package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.util.Logger;

import java.awt.Dimension;
import java.awt.Image;
import java.util.EnumSet;

public class TvSerieLocalSeasonImageAsyncJob extends AbstractImageLoaderAsyncJob {

	private static final long serialVersionUID = 1042321164421715360L;
	
	private final TvSerieSeason season;
	private final Dimension size;
	
	private Image image;
	
	public TvSerieLocalSeasonImageAsyncJob(TvSerieSeason season, Dimension size) {
		super(season.getId(), EnumSet.of(LoadType.Directory), null);
	
		this.season = season;
		this.size = size;
	}

	@Override
	protected void execute() {
		try {
			Logger.log("-> executing local season %s\n", this.season.getTvSerie().getTvSeriePathEntity().getId());
			
			this.image = getImage(this.season.getTvSerie().getTvSeriePathEntity().getPath(), TvSerieHelper.getSeasonPosterFilename(this.season), null, null, this.size);
		}
		catch (Exception e) {
			this.exception = e;
		}
	}
	
	public TvSerieSeason getSeason() {
		return this.season;
	}

	public Image getImage() {
		return this.image;
	}
	
}
