package it.ninjatech.kvo.test;

import static org.assertj.core.api.Assertions.assertThat;
import it.ninjatech.kvo.connector.myapifilms.MyApiFilmsManager;
import it.ninjatech.kvo.connector.myapifilms.model.MyApiFilmsPerson;

import org.junit.BeforeClass;
import org.junit.Test;

public class MyApiFilmsManagerUT {

	private static MyApiFilmsManager connector;
	
	@BeforeClass
    public static void start() throws Exception {
		connector = MyApiFilmsManager.getInstance();
    }
	
	@Test
	public void searchActor() throws Exception {
		MyApiFilmsPerson imdbId = connector.searchForPerson("nm2643685");
		assertThat(imdbId).isNotNull();
	}
	
}
