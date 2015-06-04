package it.ninjatech.kvo.connector.thetvdb.model;

public class TvSerie {

	/*
	 * ****************************
	 * Base
	 * ****************************
	 * id
An unsigned integer assigned by our site to the series. It does not change and will always represent the same series. Cannot be null.
Actors
A pipe delimited string of actors in plain text. Begins and ends with a pipe even if no actors are listed. Cannot be null.
Airs_DayOfWeek
The full name in English for the day of the week the series airs in plain text. Can be null.
Airs_Time
A string indicating the time of day the series airs on its original network. Format "HH:MM AM/PM". Can be null.
ContentRating
The rating given to the series based on the US rating system. Can be null or a 4-5 character string.
FirstAired
A string containing the date the series first aired in plain text using the format "YYYY-MM-DD". Can be null.
Genre
Pipe delimited list of genres in plain text. Begins and ends with a | but may also be null.
IMDB_ID
An alphanumeric string containing the IMDB ID for the series. Can be null.
Language
A two character string indicating the language in accordance with ISO-639-1. Cannot be null.
Network
A string containing the network name in plain text. Can be null.
NetworkID
Not in use, will be an unsigned integer if ever used. Can be null.
Overview
A string containing the overview in the language requested. Will return the English overview if no translation is available in the language requested. Can be null.
Rating
The average rating our users have rated the series out of 10, rounded to 1 decimal place. Can be null.
RatingCount
An unsigned integer representing the number of users who have rated the series. Can be null.
Runtime
An unsigned integer representing the runtime of the series in minutes. Can be null.
SeriesID
Deprecated. An unsigned integer representing the series ID at tv.com. As TV.com now only uses these ID's internally it's of little use and no longer updated. Can be null.
SeriesName
A string containing the series name in the language you requested. Will return the English name if no translation is found in the language requested. Can be null if the name isn't known in the requested language or English.
Status
A string containing either "Ended" or "Continuing". Can be null.
added
A string containing the date/time the series was added to our site in the format "YYYY-MM-DD HH:MM:SS" based on a 24 hour clock. Is null for older series.
addedBy
An unsigned integer. The ID of the user on our site who added the series to our database. Is null for older series.
banner
A string which should be appended to <mirrorpath>/banners/ to determine the actual location of the artwork. Returns the highest voted banner for the requested series. Can be null.
fanart
A string which should be appended to <mirrorpath>/banners/ to determine the actual location of the artwork. Returns the highest voted fanart for the requested series. Can be null.
lastupdated
Unix time stamp indicating the last time any changes were made to the series. Can be null.
posters
A string which should be appended to <mirrorpath>/banners/ to determine the actual location of the artwork. Returns the highest voted poster for the requested series. Can be null.
zap2it_id
An alphanumeric string containing the zap2it id. Can be null.


<?xml version="1.0" encoding="UTF-8" ?>
<Series>
  <id>80348</id> 
  <Actors>|Zachary Levi|Yvonne Strahovski|Joshua Gomez|Adam Baldwin|</Actors> 
  <Airs_DayOfWeek>Friday</Airs_DayOfWeek> 
  <Airs_Time>8:00 PM</Airs_Time> 
  <ContentRating>TV-PG</ContentRating> 
  <FirstAired>2007-09-24</FirstAired> 
  <Genre>|Action|Adventure|Comedy|Drama|</Genre> 
  <IMDB_ID>tt0934814</IMDB_ID> 
  <Language>en</Language> 
  <Network>NBC</Network> 
  <NetworkID /> 
  <Overview>Chuck Bartowski, ace computer geek at Buy More, is not in his...</Overview> 
  <Rating>8.8</Rating> 
  <RatingCount>818</RatingCount> 
  <Runtime>60</Runtime> 
  <SeriesID>68724</SeriesID> 
  <SeriesName>Chuck</SeriesName> 
  <Status>Ended</Status> 
  <added /> 
  <addedBy /> 
  <banner>graphical/80348-g32.jpg</banner> 
  <fanart>fanart/original/80348-48.jpg</fanart> 
  <lastupdated>1369940177</lastupdated> 
  <poster>posters/80348-16.jpg</poster> 
  <zap2it_id>EP00930779</zap2it_id> 
</Series>


	***********************************
	*Episode
	***********************************
	*Special Fields

The fields airsafter_season, airsbefore_episode, and airsbefore_season will only be included when the episode is listed as a special. Specials are also listed as being in season 0, so they're easy to identify and sort.

filename can be appended to <mirrorpath>/banners/ to get the actual location of the episode image.
DVD_episodenumber is a decimal and can be used to join episodes together. Usually used to join episodes that aired as two episodes but were released on DVD as a single episode. Normally there would be no decimal value but if you see an episode 1.1 and 1.2 that means both records should be combined to make episode 1. Cartoons are also known to combine up to 9 episodes together, for example Animaniacs season two.
Parameters

id
An unsigned integer assigned by our site to the episode. Cannot be null.
Combined_episodenumber
An unsigned integer or decimal. Cannot be null. This returns the value of DVD_episodenumber if that field is not null. Otherwise it returns the value from EpisodeNumber. The field can be used as a simple way of prioritizing DVD order over aired order in your program. In general it's best to avoid using this field as you can accomplish the same task locally and have more control if you use the DVD_episodenumber and EpisodeNumber fields separately.
Combined_season
An unsigned integer or decimal. Cannot be null. This returns the value of DVD_season if that field is not null. Otherwise it returns the value from SeasonNumber. The field can be used as a simple way of prioritizing DVD order over aired order in your program. In general it's best to avoid using this field as you can accomplish the same task locally and have more control if you use the DVD_season and SeasonNumber fields separately.
DVD_chapter
Deprecated, was meant to be used to aid in scrapping of actual DVD's but has never been populated properly. Any information returned in this field shouldn't be trusted. Will usually be null.
DVD_discid
Deprecated, was meant to be used to aid in scrapping of actual DVD's but has never been populated properly. Any information returned in this field shouldn't be trusted. Will usually be null.
DVD_episodenumber
A decimal with one decimal and can be used to join episodes together. Can be null, usually used to join episodes that aired as two episodes but were released on DVD as a single episode. If you see an episode 1.1 and 1.2 that means both records should be combined to make episode 1. Cartoons are also known to combine up to 9 episodes together, for example Animaniacs season two.
DVD_season
An unsigned integer indicating the season the episode was in according to the DVD release. Usually is the same as EpisodeNumber but can be different.
Director
A pipe delimited string of directors in plain text. Can be null.
EpImgFlag
An unsigned integer from 1-6.
1. 4:3 - Indicates an image is a proper 4:3 (1.31 to 1.35) aspect ratio.
2. 16:9 - Indicates an image is a proper 16:9 (1.739 to 1.818) aspect ratio.
3. Invalid Aspect Ratio - Indicates anything not in a 4:3 or 16:9 ratio. We don't bother listing any other non standard ratios.
4. Image too Small - Just means the image is smaller then 300x170.
5. Black Bars - Indicates there are black bars along one or all four sides of the image.
6. Improper Action Shot - Could mean a number of things, usually used when someone uploads a promotional picture that isn't actually from that episode but does refrence the episode, it could also mean it's a credit shot or that there is writting all over it. It's rarely used since most times an image would just be outright deleted if it falls in this category.
It can also be null. If it's 1 or 2 the site assumes it's a proper image, anything above 2 is considered incorrect and can be replaced by anyone with an account.
EpisodeName
A string containing the episode name in the language requested. Will return the English name if no translation is available in the language requested.
EpisodeNumber
An unsigned integer representing the episode number in its season according to the aired order. Cannot be null.
FirstAired
A string containing the date the series first aired in plain text using the format "YYYY-MM-DD". Can be null.
GuestStars
A pipe delimited string of guest stars in plain text. Can be null.
IMDB_ID
An alphanumeric string containing the IMDB ID for the series. Can be null.
Language
A two character string indicating the language in accordance with ISO-639-1. Cannot be null.
Overview
A string containing the overview in the language requested. Will return the English overview if no translation is available in the language requested. Can be null.
ProductionCode
An alphanumeric string. Can be null.
Rating
The average rating our users have rated the series out of 10, rounded to 1 decimal place. Can be null.
RatingCount
An unsigned integer representing the number of users who have rated the series. Can be null.
SeasonNumber
An unsigned integer representing the season number for the episode according to the aired order. Cannot be null.
Writer
A pipe delimited string of writers in plain text. Can be null.
absolute_number
An unsigned integer. Can be null. Indicates the absolute episode number and completely ignores seasons. In others words a series with 20 episodes per season will have Season 3 episode 10 listed as 50. The field is mostly used with cartoons and anime series as they may have ambiguous seasons making it easier to use this field.
airsafter_season
An unsigned integer indicating the season number this episode comes after. This field is only available for special episodes. Can be null.
airsbefore_episode
An unsigned integer indicating the episode number this special episode airs before. Must be used in conjunction with airsbefore_season, do not with airsafter_season. This field is only available for special episodes. Can be null.
airsbefore_season
An unsigned integer indicating the season number this special episode airs before. Should be used in conjunction with airsbefore_episode for exact placement. This field is only available for special episodes. Can be null.
filename
A string which should be appended to <mirrorpath>/banners/ to determine the actual location of the artwork. Returns the location of the episode image. Can be null.
lastupdated
Unix time stamp indicating the last time any changes were made to the episode. Can be null.
seasonid
An unsigned integer assigned by our site to the season. Cannot be null.
seriesid
An unsigned integer assigned by our site to the series. It does not change and will always represent the same series. Cannot be null.
thumb_added
A string containing the time the episode image was added to our site in the format "YYYY-MM-DD HH:MM:SS" based on a 24 hour clock. Can be null.
thumb_height
An unsigned integer that represents the height of the episode image in pixels. Can be null
thumb_width
An unsigned integer that represents the width of the episode image in pixels. Can be null

<?xml version="1.0" encoding="UTF-8" ?>
 <Episode>
    <id>533011</id> 
    <Combined_episodenumber>14</Combined_episodenumber> 
    <Combined_season>0</Combined_season> 
    <DVD_chapter /> 
    <DVD_discid /> 
    <DVD_episodenumber /> 
    <DVD_season /> 
    <Director>Graeme Harper</Director> 
    <EpImgFlag>2</EpImgFlag> 
    <EpisodeName>The Waters of Mars</EpisodeName> 
    <EpisodeNumber>14</EpisodeNumber> 
    <FirstAired>2009-11-15</FirstAired> 
    <GuestStars>Lindsay Duncan|Peter O'Brien|Sharon Duncan Brewster</GuestStars> 
    <IMDB_ID /> 
    <Language>en</Language> 
    <Overview>Location: Bowie Base One, Mars Date: 21st November 2059 Enemies: The Flood The Doctor...</Overview> 
    <ProductionCode /> 
    <Rating>8.0</Rating> 
    <RatingCount>43</RatingCount> 
    <SeasonNumber>0</SeasonNumber> 
    <Writer>Russell T Davies|Phil Ford</Writer> 
    <absolute_number /> 
    <airsafter_season>4</airsafter_season> 
    <airsbefore_episode /> 
    <airsbefore_season /> 
    <filename>episodes/78804/533011.jpg</filename> 
    <lastupdated>1378146306</lastupdated> 
    <seasonid>26260</seasonid> 
    <seriesid>78804</seriesid> 
    <thumb_added>2013-11-22 11:17:43</thumb_added> 
    <thumb_height>225</thumb_height> 
    <thumb_width>400</thumb_width> 
</Episode>
	 */
	
}
