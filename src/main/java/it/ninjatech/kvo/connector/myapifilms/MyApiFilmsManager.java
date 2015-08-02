package it.ninjatech.kvo.connector.myapifilms;

import java.io.File;

import it.ninjatech.kvo.connector.myapifilms.model.MyApiFilmsPerson;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class MyApiFilmsManager {

	public static final String BASE_URL = "http://www.myapifilms.com/";

	private static MyApiFilmsManager self;

	public static MyApiFilmsManager getInstance() {
		return self == null ? self = new MyApiFilmsManager() : self;
	}

	private final WebResource webResource;
	private boolean enabled;

	private MyApiFilmsManager() {
		this.webResource = Client.create().resource(BASE_URL);
		this.enabled = true;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return this.enabled;
	}
	
	public boolean isActive() {
		return this.enabled /*&& this.active*/;
	}

	public MyApiFilmsPerson searchForPerson(String imdbId) {
		MyApiFilmsPerson result = null;

		result = this.webResource.
				path("/imdb").
				queryParam("idName", imdbId).
				queryParam("format", "JSON").
				accept(MediaType.APPLICATION_JSON).
				get(MyApiFilmsPerson.class);

		return result;
	}
	
	public File getImage(String path) {
		return Client.create().resource(path).get(File.class);
	}

}
