package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.model.TvSeriePathEntity;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TvSerieLocalFanartAsyncJob extends AsyncJob {

	private static final long serialVersionUID = 1042321164421715360L;
	private static final String BANNER = "banner.jpg";
	private static final String CHARACTER = "character.png";
	private static final String CLEARART = "clearart.png";
	private static final String FANART = "fanart.jpg";
	private static final String LANDSCAPE = "landscape.jpg";
	private static final String LOGO = "logo.png";
	private static final String POSTER = "poster.jpg";
	
	private final TvSeriePathEntity tvSeriePathEntity;
	private final Dimension bannerSize;
	private final Dimension characterSize;
	private final Dimension clearartSize;
	private final Dimension fanartSize;
	private final Dimension landscapeSize;
	private final Dimension logoSize;
	private final Dimension posterSize;
	
	private Image banner;
	private Image character;
	private Image clearart;
	private Image fanart;
	private Image landscape;
	private Image logo;
	private Image poster;
	
	public TvSerieLocalFanartAsyncJob(TvSeriePathEntity tvSeriePathEntity, Dimension bannerSize, Dimension characterSize, Dimension clearartSize, Dimension fanartSize, Dimension landscapeSize, Dimension logoSize, Dimension posterSize) {
		super();
	
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.bannerSize = bannerSize;
		this.characterSize = characterSize;
		this.clearartSize = clearartSize;
		this.fanartSize = fanartSize;
		this.landscapeSize = landscapeSize;
		this.logoSize = logoSize;
		this.posterSize = posterSize;
	}

	public Image getBanner() {
		return this.banner;
	}

	public Image getCharacter() {
		return this.character;
	}

	public Image getClearart() {
		return this.clearart;
	}

	public Image getFanart() {
		return this.fanart;
	}

	public Image getLandscape() {
		return this.landscape;
	}

	public Image getLogo() {
		return this.logo;
	}

	public Image getPoster() {
		return this.poster;
	}

	@Override
	protected void execute() {
		try {
			System.out.printf("-> executing %s\n", this.tvSeriePathEntity.getId());
			this.banner = getImage(BANNER, bannerSize);
			this.character = getImage(CHARACTER, characterSize);
			this.clearart = getImage(CLEARART, clearartSize);
			this.fanart = getImage(FANART, fanartSize);
			this.landscape = getImage(LANDSCAPE, landscapeSize);
			this.logo = getImage(LOGO, logoSize);
			this.poster = getImage(POSTER, posterSize);
		}
		catch (Exception e) {
			this.exception = e;
		}
	}
	
	private Image getImage(String name, Dimension size) throws IOException {
		Image result = null;
		
		File image = new File(this.tvSeriePathEntity.getPath(), name);
		if (image.exists()) {
			System.out.printf("-> [%s] image %s found in directory\n", this.tvSeriePathEntity.getId(), name);
			result = ImageIO.read(image);
			
			result = result.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
		}
		
		return result;
	}
	
}
