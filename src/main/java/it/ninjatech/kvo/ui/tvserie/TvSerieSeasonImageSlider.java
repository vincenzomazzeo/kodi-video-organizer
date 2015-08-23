package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.component.AbstractImageSlider;
import it.ninjatech.kvo.ui.component.SliderPane;
import it.ninjatech.kvo.util.Labels;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.image.WebImage;
import com.alee.extended.panel.WebOverlay;
import com.alee.laf.panel.WebPanel;

public class TvSerieSeasonImageSlider extends AbstractImageSlider<TvSerieSeason> {

	private static final long serialVersionUID = 8674772929291186163L;

	protected TvSerieSeasonImageSlider(String id, ImageSliderListener listener) {
		super(id, listener, true, true, true, true);
	}

	@Override
	public Dimension getImageSize(TvSerieSeason data) {
		return Dimensions.getTvSerieSeasonSliderSize();
	}

	@Override
	protected WebPanel makeTitle(TvSerieSeason data) {
		return makeTitlePane(Labels.getTvSerieSeason(data));
	}

	@Override
	protected String getTooltip(TvSerieSeason data) {
		String result = null;

		if (TvSerieHelper.existsLocalSeason(data)) {
			if (TvSerieHelper.existsLocalSeasonPoster(data)) {
				result = Labels.TOOLTIP_HANDLE_FULL;
			}
			else {
				result = Labels.TOOLTIP_HANDLE;
			}
		}
		else {
			if (TvSerieHelper.existsLocalSeasonPoster(data)) {
				result = Labels.TOOLTIP_CREATE_FULL;
			}
			else {
				result = Labels.TOOLTIP_CREATE;
			}
		}

		return result;
	}

	@Override
	protected SliderPane makePane(TvSerieSeason season, ImageIcon voidImage, Dimension size, WebPanel titlePane) {
		return new SeasonSliderPane(season, voidImage, size, titlePane);
	}
	
	protected void refresh(TvSerieSeason season) {
		SeasonSliderPane pane = (SeasonSliderPane)this.panes.get(season);
		pane.refresh();
	}
	
	private static class SeasonSliderPane extends SliderPane {
		
		private static final long serialVersionUID = 8988800553557096192L;
		
		private WebImage buttonImage;
		private WebOverlay currentContent;
		
		public SeasonSliderPane(TvSerieSeason season, ImageIcon voidImage, Dimension size, WebPanel titlePane) {
			super(season, voidImage, size, titlePane);
		}

		@Override
		protected JComponent makeVoidComponent(ImageIcon voidImage, Dimension size) {
			makeButtonImage();
			JComponent image = super.makeVoidComponent(voidImage, size);
			
			this.currentContent = new WebOverlay(image, this.buttonImage, SwingConstants.RIGHT, SwingConstants.BOTTOM);
			this.currentContent.setBackground(Colors.TRANSPARENT);
			
			return this.currentContent;
		}

		@Override
		public void setImage(WebDecoratedImage image) {
			this.currentContent = new WebOverlay(image, this.buttonImage, SwingConstants.RIGHT, SwingConstants.BOTTOM);
			this.currentContent.setBackground(Colors.TRANSPARENT);
			
			setComponent(this.currentContent);
		}
		
		private void refresh() {
			this.currentContent.removeOverlay(this.buttonImage);
			makeButtonImage();
			this.currentContent.addOverlay(this.buttonImage, SwingConstants.RIGHT, SwingConstants.BOTTOM);
			this.currentContent.revalidate();
			this.currentContent.repaint();
		}
		
		private void makeButtonImage() {
			TvSerieSeason season = (TvSerieSeason)getData();
			if (TvSerieHelper.existsLocalSeason(season)) {
				if (TvSerieHelper.isLocalSeasonComplete(season)) {
					this.buttonImage = new WebImage(ImageRetriever.retrieveTvSerieSeasonGreenButton());
				}
				else {
					this.buttonImage = new WebImage(ImageRetriever.retrieveTvSerieSeasonOrangeButton());
				}
			}
			else {
				this.buttonImage = new WebImage(ImageRetriever.retrieveTvSerieSeasonRedButton());
			}
		}
		
	}
	
}
