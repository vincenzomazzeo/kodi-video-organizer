package it.ninjatech.kvo.connector.imdb;

import it.ninjatech.kvo.connector.imdb.model.ImdbActors;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

public class ImdbManager {

	public static final String BASE_URL = "http://www.imdb.com";
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	private static ImdbManager self;

	public static ImdbManager getInstance() {
		return self == null ? self = new ImdbManager() : self;
	}

	public static String getTitleUrl(String id) {
		return String.format("%s/title/%s", BASE_URL, id);
	}

	public static String getNameUrl(String id) {
		return String.format("%s/name/%s", BASE_URL, id);
	}
	
	private final WebResource webResource;
	private boolean enabled;

	private ImdbManager() {
		this.webResource = Client.create().resource(BASE_URL);
		this.enabled = true;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}

	public String searchForActor(String name) {
		String result = null;

		ClientResponse response = this.webResource.
				path("/xml").
				path("/find").
				queryParam("json", "1").
				queryParam("nr", "1").
				queryParam("nm", "on").
				queryParam("q", name).
				header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36").
				accept(MediaType.TEXT_HTML).
				get(ClientResponse.class);

		if (response.getStatus() == Status.OK.getStatusCode()) {
			String json = response.getEntity(String.class);
			if (StringUtils.isNotBlank(json)) {
				try {
					ImdbActors actors = OBJECT_MAPPER.readValue(json, ImdbActors.class);
					result = actors.getImdbId(name);
				}
				catch (IOException e) {
				}
			}
		}

		return result;
	}

}
