package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;

import java.awt.Dimension;
import java.awt.Image;
import java.util.EnumSet;

public class TvSerieLocalSeasonImageAsyncJob extends AbstractTvSerieImageLoaderAsyncJob {

	private static final long serialVersionUID = 1042321164421715360L;
	
	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieSeason season;
	private final Dimension size;
	
	private Image image;
	
	public TvSerieLocalSeasonImageAsyncJob(TvSeriePathEntity tvSeriePathEntity, TvSerieSeason season, Dimension size) {
		super(tvSeriePathEntity.getId(), EnumSet.of(LoadType.Directory), null);
	
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.season = season;
		this.size = size;
	}

	@Override
	protected void execute() {
		try {
			System.out.printf("-> executing local season %s\n", this.tvSeriePathEntity.getId());
			
			this.image = getImage(this.tvSeriePathEntity.getPath(), this.season.getPosterFilename(), null, null, this.size);
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
