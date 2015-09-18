package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSerieFanart;
import it.ninjatech.kvo.util.Logger;

import java.awt.Dimension;
import java.awt.Image;
import java.util.EnumSet;

public class TvSerieLocalFanartAsyncJob extends AbstractImageLoaderAsyncJob {

	private static final long serialVersionUID = 1042321164421715360L;
	
	private final TvSerie tvSerie;
	private final TvSerieFanart fanart;
	private final Dimension size;
	
	private Image image;
	
	public TvSerieLocalFanartAsyncJob(TvSerie tvSerie, TvSerieFanart fanart, Dimension size) {
		super(String.format("%s_%s", tvSerie.getTvSeriePathEntity().getId(), fanart), EnumSet.of(LoadType.Directory), null);
	
		this.tvSerie = tvSerie;
		this.fanart = fanart;
		this.size = size;
	}

	@Override
	protected void execute() {
		try {
			Logger.log("-> executing local fanart %s\n", this.tvSerie.getTvSeriePathEntity().getId());
			
			this.image = getImage(this.tvSerie.getTvSeriePathEntity().getPath(), this.fanart.getFilename(), null, null, this.size);
		}
		catch (Exception e) {
			this.exception = e;
		}
	}
	
	public TvSerieFanart getFanart() {
		return this.fanart;
	}

	public Image getImage() {
		return this.image;
	}
	
}
