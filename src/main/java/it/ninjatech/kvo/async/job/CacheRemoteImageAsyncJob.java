package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.model.ImageProvider;
import it.ninjatech.kvo.util.Logger;

import java.awt.Dimension;
import java.awt.Image;
import java.util.EnumSet;

public class CacheRemoteImageAsyncJob extends AbstractImageLoaderAsyncJob {

	private static final long serialVersionUID = -8459315395025635686L;

	private final String path;
	private final String type;
	private final Dimension size;
	
	private Image image;
	
	public CacheRemoteImageAsyncJob(String id, ImageProvider provider, String path, Dimension size, String type) {
		super(id, EnumSet.of(LoadType.Cache, LoadType.Remote), provider);
		
		this.path = path;
		this.size = size;
		this.type = type;
	}

	@Override
	protected void execute() {
		try {
			Logger.log("-> executing cache-remote image %s\n", this.id);
			
			this.image = getImage(null, null, this.id, this.path, this.size, this.type);
		}
		catch (Exception e) {
			this.exception = e;
		}
	}
	
	public Image getImage() {
		return this.image;
	}
	
}
