package it.ninjatech.kvo.async.job;

import java.awt.Dimension;
import java.awt.Image;
import java.util.EnumSet;

public class TvSerieCacheRemoteImageAsyncJob extends AbstractTvSerieImageLoaderAsyncJob {

	private static final long serialVersionUID = -8459315395025635686L;

	private final String path;
	private final Dimension size;
	
	private Image image;
	
	public TvSerieCacheRemoteImageAsyncJob(String id, String path, Dimension size) {
		super(id, EnumSet.of(LoadType.Cache, LoadType.Remote));
		
		this.path = path;
		this.size = size;
	}

	@Override
	protected void execute() {
		try {
			System.out.printf("-> executing cache-remote image %s\n", this.id);
			
			this.image = getImage(null, null, this.id, this.path, this.size);
		}
		catch (Exception e) {
			this.exception = e;
		}
	}
	
	public Image getImage() {
		return this.image;
	}
	
}
