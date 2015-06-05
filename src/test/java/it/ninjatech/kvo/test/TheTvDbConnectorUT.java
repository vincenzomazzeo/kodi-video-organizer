package it.ninjatech.kvo.test;

import static org.assertj.core.api.Assertions.assertThat;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbConnector;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbActors;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbBanners;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbLanguages;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbTvSerie;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbTvSeriesSearchResult;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.utils.LanguageMap;

import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.Test;

public class TheTvDbConnectorUT {

	public static void main(String[] args) throws Exception {
		for (Locale locale : Locale.getAvailableLocales()) {
			System.out.printf("[%s] %s - %s\n", locale.getLanguage(), locale.getDisplayLanguage(), locale.getVariant());
		}
		
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
		JAXBContext context = JAXBContext.newInstance(TheTvDbLanguages.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TheTvDbLanguages result = (TheTvDbLanguages)unmarshaller.unmarshal(TheTvDbConnectorUT.class.getClassLoader().getResourceAsStream("thetvdb_languages.xml"));
		
		assertThat(result).isNotNull();
		assertThat(result.toLanguages()).isNotEmpty();
	}
	
	@Test
	public void getLanguages() throws Exception {
		List<Locale> languages = connector.getLanguages();
		
		assertThat(languages).isNotEmpty();
	}
	
	@Test
	public void tvSeriesSearchResultUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(TheTvDbTvSeriesSearchResult.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TheTvDbTvSeriesSearchResult result = (TheTvDbTvSeriesSearchResult)unmarshaller.unmarshal(TheTvDbConnectorUT.class.getClassLoader().getResourceAsStream("thetvdb_tvseriessearchresult.xml"));
		
		assertThat(result).isNotNull();
		assertThat(result.toTvSeries()).isNotEmpty();
	}
	
	@Test
	public void searchTvSeries() throws Exception {
		List<TvSerie> result = connector.search("game of", null);
		
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();	
		
		result = connector.search("trono", LanguageMap.getInstance().getLanguage("it"));
		
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
	}
	
	@Test
	public void tvSerieUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(TheTvDbTvSerie.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TheTvDbTvSerie result = (TheTvDbTvSerie)unmarshaller.unmarshal(TheTvDbConnectorUT.class.getClassLoader().getResourceAsStream("thetvdb_tvserie.xml"));
		
		assertThat(result).isNotNull();
	}
	
	@Test
	public void fillTvSerie() throws Exception {
		List<TvSerie> result = connector.search("trono", LanguageMap.getInstance().getLanguage("it"));
		
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
		
		for (TvSerie tvSerie : result) {
			if (tvSerie.getName().equalsIgnoreCase("il trono di spade")) {
				connector.getData(tvSerie);
				
				assertThat(tvSerie.getName()).isEqualToIgnoringCase("il trono di spade");
				assertThat(tvSerie.getFirstAired()).isEqualTo("2011-04-17");
			}
		}
	}
	
	@Test
	public void bannersUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(TheTvDbBanners.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TheTvDbBanners result = (TheTvDbBanners)unmarshaller.unmarshal(TheTvDbConnectorUT.class.getClassLoader().getResourceAsStream("thetvdb_banners.xml"));
		
		assertThat(result).isNotNull();
	}
	
	@Test
	public void actorsUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(TheTvDbActors.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TheTvDbActors result = (TheTvDbActors)unmarshaller.unmarshal(TheTvDbConnectorUT.class.getClassLoader().getResourceAsStream("thetvdb_actors.xml"));
		
		assertThat(result).isNotNull();
	}
	
}
