package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.util.Utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

public class TvSerieTileImagesAsyncJob extends AsyncJob {

	private static final long serialVersionUID = 3590654815985906200L;
	private static final String FANTART = "fanart.jpg";
	private static final String POSTER = "poster.jpg";

	private final TvSeriePathEntity tvSeriePathEntity;
	private final Dimension tileSize;
	private final Dimension tilePosterSize;
	
	private Exception exception;
	private Image fanart;
	private Image poster;
	
	public TvSerieTileImagesAsyncJob(TvSeriePathEntity tvSeriePathEntity, Dimension tileSize, Dimension tilePosterSize) {
		super();
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.tileSize = tileSize;
		this.tilePosterSize = tilePosterSize;
	}

	public boolean hasException() {
		return this.exception != null;
	}
	
	public Exception getException() {
		return this.exception;
	}
	
	public Image getFanart() {
		return this.fanart;
	}

	public Image getPoster() {
		return this.poster;
	}

	@Override
	protected void execute() {
		try {
			System.out.printf("-> executing %s\n", this.tvSeriePathEntity.getId());
			BufferedImage fanart = getImage(FANTART, this.tvSeriePathEntity.getTvSerie().getFanart());
			BufferedImage poster = getImage(POSTER, this.tvSeriePathEntity.getTvSerie().getPoster());
			
			if (fanart != null) {
				this.fanart = fanart.getScaledInstance(this.tileSize.width, this.tileSize.height, Image.SCALE_SMOOTH);
			}
			if (poster != null) {
				this.poster = poster.getScaledInstance(this.tilePosterSize.width, this.tilePosterSize.height, Image.SCALE_SMOOTH);
			}
		}
		catch (Exception e) {
			this.exception = e;
		}
	}
	
	private BufferedImage getImage(String name, String remoteName) throws IOException {
		BufferedImage result = null;
		
		// Check tv serie directory
		File image = new File(this.tvSeriePathEntity.getPath(), name);
		if (image.exists()) {
			System.out.printf("-> [%s] image %s found in directory\n", this.tvSeriePathEntity.getId(), name);
			result = ImageIO.read(image);
		}
		else {
			// Check cache
			image = new File(Utils.getCacheDirectory(), String.format("%s_%s", this.tvSeriePathEntity.getId(), name));
			if (image.exists()) {
				System.out.printf("-> [%s] image %s found in cache\n", this.tvSeriePathEntity.getId(), name);
				result = ImageIO.read(image);
			}
			else {
				// Get from TheTVDB
				if (TheTvDbManager.getInstance().isActive() && StringUtils.isNotBlank(remoteName)) {
					image = TheTvDbManager.getInstance().getImage(remoteName);
					result = ImageIO.read(image);
					// Save in cache
					image = new File(Utils.getCacheDirectory(), String.format("%s_%s", this.tvSeriePathEntity.getId(), name));
					image.deleteOnExit();
					ImageIO.write(result, "jpg", image);
					System.out.printf("-> [%s] image %s requested to remote host\n", this.tvSeriePathEntity.getId(), name);
				}
			}
		}
		
		return result;
	}
	
}