package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerieActor;
import it.ninjatech.kvo.util.Utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

public class TvSerieActorsAsyncJob extends AbstractTvSerieImageLoaderAsyncJob {

	private static final long serialVersionUID = -8459315395025635686L;

	private final TvSerieActor actor;
	private final Dimension size;
	
	private Image image;
	
	public TvSerieActorsAsyncJob(TvSerieActor actor, Dimension size) {
		super(actor.getId(), EnumSet.of(LoadType.Cache, LoadType.Remote));
		
		this.actor = actor;
		this.size = size;
	}

	@Override
	protected void execute() {
		try {
			System.out.printf("-> executing actor %s\n", this.actor.getId());
			
			this.image = getImage(null, null, this.actor.getId(), this.actor.getPath(), this.size);
		}
		catch (Exception e) {
			this.exception = e;
		}
	}
	
	public TvSerieActor getActor() {
		return this.actor;
	}

	public Image getImage() {
		return this.image;
	}
	
	private Image getImage(String id, String remoteName) throws IOException {
		Image result = null;
		
		// Check cache
		File image = new File(Utils.getCacheDirectory(), id);
		if (image.exists()) {
			System.out.printf("-> [%s] actor %s found in cache\n", this.actor.getId(), id);
			result = ImageIO.read(image);
		}
		else {
			// Get from TheTVDB
			if (TheTvDbManager.getInstance().isActive() && StringUtils.isNotBlank(remoteName)) {
				image = TheTvDbManager.getInstance().getImage(remoteName);
				result = ImageIO.read(image);
				// Save in cache
				image = new File(Utils.getCacheDirectory(), id);
				image.deleteOnExit();
				ImageIO.write((BufferedImage)result, "jpg", image);
				System.out.printf("-> [%s] actor %s requested to remote host\n", this.actor.getId(), id);
			}
		}
		
		if (result != null) {
			result = result.getScaledInstance(this.size.width, this.size.height, Image.SCALE_SMOOTH);
		}
		
		return result;
	}
	
}
