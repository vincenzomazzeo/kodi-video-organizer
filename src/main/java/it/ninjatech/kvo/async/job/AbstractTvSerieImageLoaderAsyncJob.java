package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerieImageProvider;
import it.ninjatech.kvo.util.Utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractTvSerieImageLoaderAsyncJob extends AsyncJob {

	private static final long serialVersionUID = -1447318423997479870L;

	protected enum LoadType {
		Cache, Directory, Remote;		
	}
	
	protected final String id;
	private final EnumSet<LoadType> loadSequence;
	private final TvSerieImageProvider provider;
	
	protected AbstractTvSerieImageLoaderAsyncJob(String id, EnumSet<LoadType> loadSequence, TvSerieImageProvider provider) {
		this.id = id;
		this.loadSequence = loadSequence;
		this.provider = provider;
	}
	
	public String getId() {
		return this.id;
	}

	protected Image getImage(String directory, String name, String cacheName, String remoteName, Dimension size) throws IOException {
		Image result = null;
		
		File image = null;
		
		Iterator<LoadType> iterator = this.loadSequence.iterator();
		while (iterator.hasNext() && result == null) {
			switch (iterator.next()) {
			case Cache:
				image = new File(Utils.getCacheDirectory(), cacheName);
				if (image.exists()) {
					System.out.printf("-> [%s] image %s found in cache\n", this.id, cacheName);
					result = ImageIO.read(image);
				}
				break;
			case Directory:
				image = new File(directory, name);
				if (image.exists()) {
					System.out.printf("-> [%s] image %s found in directory\n", this.id, name);
					result = ImageIO.read(image);
				}
				break;
			case Remote:
				if (this.provider == TvSerieImageProvider.Fanarttv && FanarttvManager.getInstance().isActive() && StringUtils.isNotBlank(remoteName)) {
					image = FanarttvManager.getInstance().getImage(remoteName);
					result = ImageIO.read(image);
				}
				else if (this.provider == TvSerieImageProvider.TheTvDb && TheTvDbManager.getInstance().isActive() && StringUtils.isNotBlank(remoteName)) {
					image = TheTvDbManager.getInstance().getImage(remoteName);
					result = ImageIO.read(image);
				}
				if (result != null) {
					// Save in cache
					image = new File(Utils.getCacheDirectory(), cacheName);
					image.deleteOnExit();
					ImageIO.write((BufferedImage)result, "jpg", image);
					System.out.printf("-> [%s] image %s requested to remote host (%s)\n", this.id, remoteName, this.provider);
				}
				break;
			}
		}
		
		if (result != null) {
			result = result.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
		}
		
		return result;
	}
	
}
