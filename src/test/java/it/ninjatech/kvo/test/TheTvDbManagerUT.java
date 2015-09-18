package it.ninjatech.kvo.test;

import static org.assertj.core.api.Assertions.assertThat;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbActors;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbBanners;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbLanguages;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbTvSerie;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbTvSeriesSearchResult;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.Test;

public class TheTvDbManagerUT {

	public static void main(String[] args) throws Exception {
//		SettingsHandler.init();
//		TheTvDbConnector connector = TheTvDbConnector.getInstance();
//		
//		connector.getLanguages();
		System.out.println("End");
	}
	
	private static TheTvDbManager connector;
	
	@BeforeClass
    public static void start() throws Exception {
		SettingsHandler.init();
		EnhancedLocaleMap.init();
		
		TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApiKey());

		connector = TheTvDbManager.getInstance();
    }
	
	@Test
	public void checkApiKey() throws Exception {
		List<EnhancedLocale> check = TheTvDbManager.getInstance().checkApiKey("dd");
		
		assertThat(check).isNull();
		
		check = TheTvDbManager.getInstance().checkApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApiKey());
		
		assertThat(check).isNotEmpty();
	}
	
	@Test
	public void languagesUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(TheTvDbLanguages.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TheTvDbLanguages result = (TheTvDbLanguages)unmarshaller.unmarshal(TheTvDbManagerUT.class.getClassLoader().getResourceAsStream("thetvdb_languages.xml"));
		
		assertThat(result).isNotNull();
		assertThat(result.toLanguages()).isNotEmpty();
	}
	
	@Test
	public void getLanguages() throws Exception {
		List<EnhancedLocale> languages = connector.getLanguages();
		
		assertThat(languages).isNotEmpty();
	}
	
	@Test
	public void tvSeriesSearchResultUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(TheTvDbTvSeriesSearchResult.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TheTvDbTvSeriesSearchResult result = (TheTvDbTvSeriesSearchResult)unmarshaller.unmarshal(TheTvDbManagerUT.class.getClassLoader().getResourceAsStream("thetvdb_tvseriessearchresult.xml"));
		
		assertThat(result).isNotNull();
		assertThat(result.toTvSeries()).isNotEmpty();
	}
	
	@Test
	public void searchTvSeries() throws Exception {
		List<TvSerie> result = connector.search("game of", EnhancedLocaleMap.getEmptyLocale());
		
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();	
		
		result = connector.search("trono", EnhancedLocaleMap.getByLanguage("it"));
		
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
	}
	
	@Test
	public void tvSerieUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(TheTvDbTvSerie.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TheTvDbTvSerie result = (TheTvDbTvSerie)unmarshaller.unmarshal(TheTvDbManagerUT.class.getClassLoader().getResourceAsStream("thetvdb_tvserie.xml"));
		
		assertThat(result).isNotNull();
	}
	
	@Test
	public void fillTvSerie() throws Exception {
		List<TvSerie> result = connector.search("trono", EnhancedLocaleMap.getByLanguage("it"));
		
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
	public void getImage() throws Exception {
		File image = connector.getImage("fanart/original/121361-83.jpg");
		
		BufferedImage i = ImageIO.read(image);
		
		JDialog frame = new JDialog();
		frame.setModal(true);

		JLabel lblimage = new JLabel(new ImageIcon(i));
		frame.getContentPane().add(lblimage, BorderLayout.CENTER);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	@Test
	public void bannersUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(TheTvDbBanners.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TheTvDbBanners result = (TheTvDbBanners)unmarshaller.unmarshal(TheTvDbManagerUT.class.getClassLoader().getResourceAsStream("thetvdb_banners.xml"));
		
		assertThat(result).isNotNull();
	}
	
	@Test
	public void actorsUnmarshall() throws Exception {
		JAXBContext context = JAXBContext.newInstance(TheTvDbActors.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TheTvDbActors result = (TheTvDbActors)unmarshaller.unmarshal(TheTvDbManagerUT.class.getClassLoader().getResourceAsStream("thetvdb_actors.xml"));
		
		assertThat(result).isNotNull();
	}
	
}
