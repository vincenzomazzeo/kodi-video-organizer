package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.model.ImageProvider;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.util.Logger;

import java.awt.Dimension;
import java.awt.Image;
import java.util.EnumSet;

public class TvSerieTileImagesAsyncJob extends AbstractImageLoaderAsyncJob {

	private static final long serialVersionUID = 3590654815985906200L;
	private static final String FANTART = "fanart.jpg";
	private static final String POSTER = "poster.jpg";

	private final TvSerie tvSerie;
	private final Dimension tileSize;
	private final Dimension tilePosterSize;
	
	private Image fanart;
	private Image poster;
	
	public TvSerieTileImagesAsyncJob(TvSerie tvSerie, Dimension tileSize, Dimension tilePosterSize) {
		super(tvSerie.getTvSeriePathEntity().getId(), EnumSet.of(LoadType.Directory, LoadType.Cache, LoadType.Remote), ImageProvider.TheTvDb);

		this.tvSerie = tvSerie;
		this.tileSize = tileSize;
		this.tilePosterSize = tilePosterSize;
	}

	@Override
	protected void execute() {
		try {
			Logger.log("-> executing tile images %s\n", this.tvSerie.getTvSeriePathEntity().getId());
			this.fanart = getImage(this.tvSerie.getTvSeriePathEntity().getPath(), FANTART, 
			                       String.format("%s_%s", this.tvSerie.getTvSeriePathEntity().getId(), FANTART), 
			                       this.tvSerie.getFanart(), 
			                       this.tileSize);
			this.poster = getImage(this.tvSerie.getTvSeriePathEntity().getPath(), POSTER, 
			                       String.format("%s_%s", this.tvSerie.getTvSeriePathEntity().getId(), POSTER), 
			                       this.tvSerie.getPoster(), 
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
