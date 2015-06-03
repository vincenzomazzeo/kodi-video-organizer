package it.ninjatech.kvo.test;

//import static org.assertj.core.api.Assertions.assertThat;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.Languages;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbConnector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

public class TheTvDbConnectorUT {

	public static void main(String[] args) throws Exception {
		SettingsHandler.init();
		TheTvDbConnector connector = TheTvDbConnector.getInstance();
		
		connector.getLanguages();
		
		System.out.println("End");
		
//		
//		
//		
//		
//		System.out.println("qui");
	}
	
	@Test
	public void languagesUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(Languages.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Languages languages = (Languages)unmarshaller.unmarshal(TheTvDbConnectorUT.class.getClassLoader().getResourceAsStream("thetvdb_languages.xml"));
		
//		assertThat("a");
	}
	
	public void getLanguages() throws Exception {
		SettingsHandler.init();
		TheTvDbConnector connector = TheTvDbConnector.getInstance();
		connector.getLanguages();
	}
	
}
