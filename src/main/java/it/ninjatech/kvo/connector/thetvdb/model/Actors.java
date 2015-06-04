package it.ninjatech.kvo.connector.thetvdb.model;

public class Actors {

	/*
	 * id
Useless field, will eventually be used to link actors between series but currently isn't in use and may actually change once it gets fully implemented so don't bother storing it at all.
Image
Can be appended to <mirrorpath>/banners/ to determine the actual location of the artwork.
Name
The actors real name.
Role
The name of the actors character in the series.
SortOrder
An integer from 0-3. 1 being the most important actor on the show and 3 being the third most important actor. 0 means they have no special sort order. Duplicates of 1-3 aren't suppose to be allowed but currently are so the field isn't perfect but can still be used for basic sorting.

<?xml version="1.0" encoding="UTF-8" ?> 
<Actors>
 <Actor>
  <id>27747</id> 
  <Image>actors/27747.jpg</Image> 
  <Name>Matthew Fox</Name> 
  <Role>Jack Shephard</Role> 
  <SortOrder>0</SortOrder> 
  </Actor>
 <Actor>
  <id>27745</id> 
  <Image>actors/27745.jpg</Image> 
  <Name>Terry O'Quinn</Name> 
  <Role>John Locke</Role> 
  <SortOrder>0</SortOrder> 
  </Actor>
 <Actor>
  <id>27741</id> 
  <Image>actors/27741.jpg</Image> 
  <Name>Evangeline Lilly</Name> 
  <Role>Kate Austen</Role> 
  <SortOrder>0</SortOrder> 
  </Actor>
 <Actor>
  <id>27749</id> 
  <Image>actors/27749.jpg</Image> 
  <Name>Naveen Andrews</Name> 
  <Role>Sayid Jarrah</Role> 
  <SortOrder>3</SortOrder> 
  </Actor>
 <Actor>
  <id>27742</id> 
  <Image>actors/27742.jpg</Image> 
  <Name>Daniel Dae Kim</Name> 
  <Role>Jin Kwon</Role> 
  <SortOrder>2</SortOrder> 
  </Actor>
 <Actor>
  <id>27740</id> 
  <Image>actors/27740.jpg</Image> 
  <Name>Yunjin Kim</Name> 
  <Role>Sun Kwon</Role> 
  <SortOrder>1</SortOrder> 
  </Actor>
</Actors>
	 *
	 *
	 */
	
}
