package it.ninjatech.kvo.test;

import static org.assertj.core.api.Assertions.assertThat;
import it.ninjatech.kvo.connector.imdb.ImdbManager;

import org.junit.BeforeClass;
import org.junit.Test;

public class ImdbManagerUT {

	private static ImdbManager connector;
	
	@BeforeClass
    public static void start() throws Exception {
		connector = ImdbManager.getInstance();
    }
	
	@Test
	public void searchActor() throws Exception {
		String imdbId = connector.searchForActor("Chad Michael Murray");
		assertThat(imdbId).isNotNull();
	}
	
}
