package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.model.TvSeriePathEntity;

import java.awt.Dimension;
import java.awt.Image;
import java.util.EnumSet;

public class TvSerieTileImagesAsyncJob extends AbstractTvSerieImageLoaderAsyncJob {

	private static final long serialVersionUID = 3590654815985906200L;
	private static final String FANTART = "fanart.jpg";
	private static final String POSTER = "poster.jpg";

	private final TvSeriePathEntity tvSeriePathEntity;
	private final Dimension tileSize;
	private final Dimension tilePosterSize;
	
	private Image fanart;
	private Image poster;
	
	public TvSerieTileImagesAsyncJob(TvSeriePathEntity tvSeriePathEntity, Dimension tileSize, Dimension tilePosterSize) {
		super(tvSeriePathEntity.getId(), EnumSet.of(LoadType.Directory, LoadType.Cache, LoadType.Remote));

		this.tvSeriePathEntity = tvSeriePathEntity;
		this.tileSize = tileSize;
		this.tilePosterSize = tilePosterSize;
	}

	@Override
	protected void execute() {
		try {
			System.out.printf("-> executing tile images %s\n", this.tvSeriePathEntity.getId());
			this.fanart = getImage(this.tvSeriePathEntity.getPath(), FANTART, 
			                       String.format("%s_%s", this.tvSeriePathEntity.getId(), FANTART), 
			                       this.tvSeriePathEntity.getTvSerie().getFanart(), 
			                       this.tileSize);
			this.poster = getImage(this.tvSeriePathEntity.getPath(), POSTER, 
			                       String.format("%s_%s", this.tvSeriePathEntity.getId(), POSTER), 
			                       this.tvSeriePathEntity.getTvSerie().getPoster(), 
			                       this.tilePosterSize);
		}
		catch (Exception e) {
			this.exception = e;
		}
	}
	
	public Image getFanart() {
		return this.fanart;
	}

	public Image getPoster() {
		return this.poster;
	}
	
}
