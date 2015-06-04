package it.ninjatech.kvo.connector.thetvdb.model;

public class Banners {

	/*
	 * BannerPath
Can be appended to <mirrorpath>/banners/ to determine the actual location of the artwork.
ThumbnailPath and VignettePath
Used exactly the same way as BannerPath, only shows if BannerType is fanart.
BannerType
This can be poster, fanart, series or season.
BannerType2
For series banners it can be text, graphical, or blank. For season banners it can be season or seasonwide. For fanart it can be 1280x720 or 1920x1080. For poster it will always be 680x1000.
Blank banners will leave the title and show logo off the banner. Text banners will show the series name as plain text in an Arial font. Graphical banners will show the series name in the show's official font or will display the actual logo for the show. Season banners are the standard DVD cover format while wide season banners will be the same dimensions as the series banners.
Language
Some banners list the series name in a foreign language. The language abbreviation will be listed here.
Season
If the banner is for a specific season, that season number will be listed here.
Rating
Returns either null or a decimal with four decimal places. The rating the banner currently has on the site.
RatingCount
Always returns an unsigned integer. Number of people who have rated the image.
SeriesName
This can be true or false. Only shows if BannerType is fanart. Indicates if the seriesname is included in the image or not.
Colors
Returns either null or three RGB colors in decimal format and pipe delimited. These are colors the artist picked that go well with the image. In order they are Light Accent Color, Dark Accent Color and Neutral Midtone Color. It's meant to be used if you want to write something over the image, it gives you a good idea of what colors may work and show up well. Only shows if BannerType is fanart.


<?xml version="1.0" encoding="UTF-8" ?>
<Banners>
   <Banner>
      <id>14820</id>
      <BannerPath>text/80348.jpg</BannerPath>
      <BannerType>series</BannerType>
      <BannerType2>text</BannerType2>
      <Language>en</Language>
      <Season></Season>
   </Banner>
   <Banner>
      <id>14821</id>
      <BannerPath>blank/80348.jpg</BannerPath>
      <BannerType>series</BannerType>
      <BannerType2>blank</BannerType2>
      <Language></Language>
      <Season></Season>
   </Banner>
   <Banner>
      <id>14827</id>
      <BannerPath>graphical/80348-g.jpg</BannerPath>
      <BannerType>series</BannerType>
      <BannerType2>graphical</BannerType2>
      <Language>en</Language>
      <Season></Season>
   </Banner>
   <Banner>
      <id>15217</id>
      <BannerPath>seasons/80348-1.jpg</BannerPath>
      <BannerType>season</BannerType>
      <BannerType2>season</BannerType2>
      <Language>en</Language>
      <Season>1</Season>
   </Banner>
   <Banner>
      <id>876076</id> 
      <BannerPath>fanart/original/80348-49.jpg</BannerPath> 
      <BannerType>fanart</BannerType> 
      <BannerType2>1920x1080</BannerType2> 
      <Colors>|81,81,81|15,15,15|201,226,246|</Colors> 
      <Language>de</Language> 
      <Rating>6.6667</Rating> 
      <RatingCount>6</RatingCount> 
      <SeriesName>false</SeriesName> 
      <ThumbnailPath>_cache/fanart/original/80348-49.jpg</ThumbnailPath> 
      <VignettePath>fanart/vignette/80348-49.jpg</VignettePath> 
   </Banner>
   <Banner>
      <id>877696</id> 
      <BannerPath>posters/80348-16.jpg</BannerPath> 
      <BannerType>poster</BannerType> 
      <BannerType2>680x1000</BannerType2> 
      <Language>en</Language> 
      <Rating>8.8000</Rating> 
      <RatingCount>5</RatingCount>
   </Banner>
</Banners>

	 */
	
}
