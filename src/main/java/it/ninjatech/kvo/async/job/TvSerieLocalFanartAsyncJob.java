package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSeriePathEntity;

import java.awt.Dimension;
import java.awt.Image;
import java.util.EnumSet;

public class TvSerieLocalFanartAsyncJob extends AbstractTvSerieImageLoaderAsyncJob {

	private static final long serialVersionUID = 1042321164421715360L;
	
	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieFanart fanart;
	private final Dimension size;
	
	private Image image;
	
	public TvSerieLocalFanartAsyncJob(TvSeriePathEntity tvSeriePathEntity, TvSerieFanart fanart, Dimension size) {
		super(String.format("%s_%s", tvSeriePathEntity.getId(), fanart), EnumSet.of(LoadType.Directory), null);
	
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.fanart = fanart;
		this.size = size;
	}

	@Override
	protected void execute() {
		try {
			System.out.printf("-> executing local fanart %s\n", this.tvSeriePathEntity.getId());
			
			this.image = getImage(this.tvSeriePathEntity.getPath(), this.fanart.getFilename(), null, null, this.size);
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
