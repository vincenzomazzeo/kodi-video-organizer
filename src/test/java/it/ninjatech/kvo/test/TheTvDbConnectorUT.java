package it.ninjatech.kvo.test;

import static org.assertj.core.api.Assertions.assertThat;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbConnector;
import it.ninjatech.kvo.connector.thetvdb.model.Languages;
import it.ninjatech.kvo.connector.thetvdb.model.TvSeriesSearchResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.Test;

public class TheTvDbConnectorUT {

	public static void main(String[] args) throws Exception {
		JAXBContext context = JAXBContext.newInstance(TvSeriesSearchResult.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TvSeriesSearchResult result = (TvSeriesSearchResult)unmarshaller.unmarshal(TheTvDbConnectorUT.class.getClassLoader().getResourceAsStream("thetvdb_tvseriessearchresult.xml"));
		
//		SettingsHandler.init();
//		TheTvDbConnector connector = TheTvDbConnector.getInstance();
//		
//		connector.getLanguages();
		System.out.println("End");
	}
	
	private static TheTvDbConnector connector;
	
	@BeforeClass
    public static void start() throws Exception {
		SettingsHandler.init();
		connector = TheTvDbConnector.getInstance();
    }
	
	@Test
	public void languagesUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(Languages.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Languages result = (Languages)unmarshaller.unmarshal(TheTvDbConnectorUT.class.getClassLoader().getResourceAsStream("thetvdb_languages.xml"));
		
		assertThat(result).isNotNull();
		assertThat(result.getLanguages()).isNotEmpty();
	}
	
	@Test
	public void getLanguages() throws Exception {
		Languages languages = connector.getLanguages();
		
		assertThat(languages).isNotNull();
		assertThat(languages.getLanguages()).isNotEmpty();
	}
	
	@Test
	public void tvSeriesSearchResultUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(TvSeriesSearchResult.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TvSeriesSearchResult result = (TvSeriesSearchResult)unmarshaller.unmarshal(TheTvDbConnectorUT.class.getClassLoader().getResourceAsStream("thetvdb_tvseriessearchresult.xml"));
		
		assertThat(result).isNotNull();
		assertThat(result.getTvSeries()).isNotEmpty();
	}
	
	@Test
	public void searchTvSeries() throws Exception {
		TvSeriesSearchResult result = connector.search("game of", null);
		
		assertThat(result).isNotNull();
		assertThat(result.getTvSeries()).isNotEmpty();
		
		result = connector.search("trono", "it");
		
		assertThat(result).isNotNull();
		assertThat(result.getTvSeries()).isNotEmpty();
	}
	
}
