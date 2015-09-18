package it.ninjatech.kvo.connector.thetvdb.model;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Banners")
public class TheTvDbBanners {

	@XmlElement(name = "Banner")
	private List<TheTvDbBanner> banners;
	
	protected TheTvDbBanners() {}
	
	public void fill(TvSerie tvSerie) {
		for (TheTvDbBanner banner : this.banners) {
			banner.fill(tvSerie);
		}
	}
	
	protected List<TheTvDbBanner> getBanners() {
		return this.banners;
	}

	protected void setBanners(List<TheTvDbBanner> banners) {
		this.banners = banners;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	protected static class TheTvDbBanner {
		
		@XmlElement(name = "BannerPath")
		private String bannerPath; // <mirrorpath>/banners/
		@XmlElement(name = "ThumbnailPath")
		private String thumbnailPath;
		@XmlElement(name = "VignettePath")
		private String vignettePath;
		//  - poster, fanart, series or season.
		@XmlElement(name = "BannerType")
		private String type;
		//  - For series banners it can be text, graphical, or blank. For season banners it can be season or seasonwide. For fanart it can be 1280x720 or 1920x1080. For poster it will always be 680x1000.
		@XmlElement(name = "BannerType2")
		private String subtype;
		@XmlElement(name = "Language")
		private String language;
		@XmlElement(name = "Season")
		private Integer season;
		@XmlElement(name = "Rating")
		private BigDecimal rating;
		@XmlElement(name = "RatingCount")
		private Integer ratingCount;
		@XmlElement(name = "SeriesName")
		private Boolean hasSeriesName;
		
		protected TheTvDbBanner() {}

		private void fill(TvSerie tvSerie) {
			String ratingCount = (this.ratingCount != null && !this.ratingCount.equals(0)) ? this.ratingCount.toString() : null;
			EnhancedLocale language = this.language != null ? EnhancedLocaleMap.getByLanguage(this.language) : EnhancedLocaleMap.getEmptyLocale();
			
			TheTvDbBannerType type = TheTvDbBannerType.parse(this.type);
			if (type == TheTvDbBannerType.Season) {
				tvSerie.addTheTvDbSeasonImage(this.bannerPath, this.season, this.rating, ratingCount, language);
			}
			else {
				tvSerie.addTheTvDbFanart(type.getFanart(), this.bannerPath, this.rating, ratingCount, language);
			}
		}
		
		protected String getBannerPath() {
			return this.bannerPath;
		}

		protected void setBannerPath(String bannerPath) {
			this.bannerPath = bannerPath;
		}

		protected String getThumbnailPath() {
			return this.thumbnailPath;
		}

		protected void setThumbnailPath(String thumbnailPath) {
			this.thumbnailPath = thumbnailPath;
		}

		protected String getVignettePath() {
			return this.vignettePath;
		}

		protected void setVignettePath(String vignettePath) {
			this.vignettePath = vignettePath;
		}

		protected String getType() {
			return this.type;
		}

		protected void setType(String type) {
			this.type = type;
		}

		protected String getSubtype() {
			return this.subtype;
		}

		protected void setSubtype(String subtype) {
			this.subtype = subtype;
		}

		protected String getLanguage() {
			return this.language;
		}

		protected void setLanguage(String language) {
			this.language = language;
		}

		protected Integer getSeason() {
			return this.season;
		}

		protected void setSeason(Integer season) {
			this.season = season;
		}

		protected BigDecimal getRating() {
			return this.rating;
		}

		protected void setRating(BigDecimal rating) {
			this.rating = rating;
		}

		protected Integer getRatingCount() {
			return this.ratingCount;
		}

		protected void setRatingCount(Integer ratingCount) {
			this.ratingCount = ratingCount;
		}

		protected Boolean getHasSeriesName() {
			return this.hasSeriesName;
		}

		protected void setHasSeriesName(Boolean hasSeriesName) {
			this.hasSeriesName = hasSeriesName;
		}
		
	}
	
}
