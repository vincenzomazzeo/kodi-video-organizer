package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.model.TvSerieFanart;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.alee.utils.SystemUtils;

public final class Dimensions {

	public static final Dimension ACTOR_FULL_SIZE = new Dimension(300, 450);
	public static final Dimension SEASON_FULL_SIZE = new Dimension(400, 578);
	public static final Dimension EPISODE_FULL_SIZE = new Dimension(400, 225);
	public static final int SCROLL_BAR_WIDTH = 20;
	
	private static final int HD_WIDTH = 1920;
	private static final int HD_HEIGHT = 1080;
	private static final BigDecimal _16_9 = new BigDecimal("1.7777");
	private static final BigDecimal _4_3 = new BigDecimal("1.3333");
	private static final int TV_SERIE_WALL_IMAGE_MAX_HEIGHT = 130;

	private static Dimension startupSize;
	private static Dimension explorerTileSize;
	private static Dimension explorerTilePosterSize;

	public static Dimension getStartupSize() {
		if (startupSize == null) {
			startupSize = null;

			DisplayMode displayMode = SystemUtils.getGraphicsConfiguration().getDevice().getDisplayMode();

			BigDecimal width = new BigDecimal(displayMode.getWidth());
			BigDecimal height = new BigDecimal(displayMode.getHeight());
			BigDecimal ratio = width.divide(height, 4, RoundingMode.DOWN);

			int startupWidth = 0;
			int startupHeight = 0;
			if (ratio.equals(_16_9)) {
				if (displayMode.getWidth() > HD_WIDTH) {
					startupHeight = HD_HEIGHT;
					startupWidth = HD_WIDTH;
				}
				else {
					startupHeight = (int)(displayMode.getHeight() * .9);
					startupWidth = (int)(displayMode.getWidth() * .9);
				}
			}
			else if (ratio.equals(_4_3)) {
				startupHeight = (int)(displayMode.getHeight() * .9);
				startupWidth = (int)(startupHeight / _16_9.doubleValue());
			}
			else {
				if (displayMode.getWidth() > displayMode.getHeight()) {
					startupHeight = (int)(displayMode.getHeight() * .9);
					startupWidth = (int)(startupHeight / _16_9.doubleValue());
				}
				else {
					startupWidth = (int)(displayMode.getHeight() * .9);
					startupHeight = (int)(startupWidth * _16_9.doubleValue());
				}
			}
			startupSize = new Dimension(startupWidth, startupHeight);
		}

		return startupSize;
	}

	public static int getExplorerWidth() {
		return getStartupSize().width / 5;
	}
	
	public static Dimension getExplorerTileSize() {
		if (explorerTileSize == null) {
			int width = getExplorerWidth() - 31;
			int height = (int)((double)(width * 9) / 16d);
			explorerTileSize = new Dimension(width, height);
		}
		
		return explorerTileSize;
	}
	
	public static Dimension getExplorerTilePosterSize() {
		if (explorerTilePosterSize == null) {
			int height = (getExplorerTileSize().height / 7) * 5;
			int width = (int)((double)height * .68d);
			explorerTilePosterSize = new Dimension(width, height);
		}
		
		return explorerTilePosterSize;
	}
	
	public static int getExplorerTilePosterMargin() {
		return (getExplorerTileSize().height - getExplorerTilePosterSize().height) / 5;
	}
	
	public static int getExplorerTileInfoHeight() {
		return getExplorerTileSize().height / 4;
	}

	public static int getFanartChoiceAvailableWidth() {
		return getStartupSize().width / 5 * 4;
	}
	
	public static int getFanartChoiceAvailableHeight() {
		return getStartupSize().height / 8 * 7;
	}
	
	public static Dimension getTvSerieSeasonHandlerSize() {
		Dimension result = new Dimension();
		
		Dimension startupSize = getStartupSize();
		result.width = startupSize.width / 10 * 9;
		result.height = startupSize.height / 10 * 9;
		
		return result;
	}
	
	public static Dimension getTvSerieFanartSliderSize(TvSerieFanart fanart) {
		return getTvSerieFanartSize(fanart.getSliderHeight(), fanart.getRealSize().width, fanart.getRealSize().height);
	}
	
	public static Dimension getTvSerieSeasonSliderSize() {
		return getTvSerieFanartSize(TV_SERIE_WALL_IMAGE_MAX_HEIGHT, SEASON_FULL_SIZE.width, SEASON_FULL_SIZE.height);
	}
	
	public static Dimension getTvSerieActorSliderSize() {
		return getTvSerieFanartSize(TV_SERIE_WALL_IMAGE_MAX_HEIGHT, ACTOR_FULL_SIZE.width, ACTOR_FULL_SIZE.height);
	}
	
	public static Dimension getTvSerieSeasonHandlerPosterSize() {
		return getTvSerieFanartSize(100, SEASON_FULL_SIZE.width, SEASON_FULL_SIZE.height);
	}
	
	public static Dimension getTvSerieFanartChooserSize(TvSerieFanart fanart) {
		Dimension result = new Dimension();
		
		result.height = fanart.getChooserHeight();
		result.width = result.height * fanart.getRealSize().width / fanart.getRealSize().height;
		
		return result;
	}
	
	public static Dimension getTvSerieSeasonEpisodeImageSize() {
		Dimension result = new Dimension();
		
		result.height = EPISODE_FULL_SIZE.height / 3;
		result.width = EPISODE_FULL_SIZE.width / 3;
		
		return result;
	}
	
	private static Dimension getTvSerieFanartSize(int preferredHeight, int realWidth, int realHeight) {
		Dimension result = new Dimension();
		
		Dimension startupSize = getStartupSize();
		int maxHeight = startupSize.height / 5;
		
		result.height = maxHeight < preferredHeight ? maxHeight : preferredHeight;
		result.width = result.height * realWidth / realHeight;
		
		return result;
	}
	
	private Dimensions() {
	}

}
