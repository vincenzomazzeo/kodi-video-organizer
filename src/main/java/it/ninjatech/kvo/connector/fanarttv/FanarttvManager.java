package it.ninjatech.kvo.connector.fanarttv;

import it.ninjatech.kvo.connector.fanarttv.model.FanarttvFanarts;
import it.ninjatech.kvo.tvserie.model.TvSerie;

import java.io.File;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class FanarttvManager {

    public static final String BASE_URL = "http://webservice.fanart.tv";

    private static FanarttvManager self;

    public static FanarttvManager getInstance() {
        return self == null ? self = new FanarttvManager() : self;
    }

    private final WebResource webResource;
    private boolean enabled;
    private boolean active;
    private String apiKey;

    private FanarttvManager() {
        this.webResource = Client.create().resource(BASE_URL);
        this.enabled = false;
        this.active = false;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isActive() {
        return this.enabled && this.active;
    }

    public void deactivate() {
        this.active = false;
        this.apiKey = null;
    }

    public boolean checkApiKey(String apiKey) {
        boolean result = false;

        ClientResponse response = this.webResource.
                path("/v3").
                path("/tv").
                path("/72158").
                queryParam("api_key", apiKey).
                type(MediaType.APPLICATION_JSON).
                get(ClientResponse.class);

        result = response.getStatus() == Status.OK.getStatusCode();

        if (result) {
            this.active = true;
            this.apiKey = apiKey;
        }

        return result;
    }

    public boolean setApiKey(String apiKey) {
        boolean result = checkApiKey(apiKey);

        if (result) {
            this.active = true;
            this.apiKey = apiKey;
        }

        return result;
    }

    public void getData(TvSerie tvSerie) {
        if (isActive()) {
            ClientResponse response = this.webResource.
                    path("/v3").
                    path("/tv").
                    path(tvSerie.getProviderId()).
                    queryParam("api_key", apiKey).
                    type(MediaType.APPLICATION_JSON).
                    get(ClientResponse.class);
            
            if (response.getStatus() == Status.OK.getStatusCode()) {
                FanarttvFanarts fanarttvFanarts = response.getEntity(FanarttvFanarts.class);
                fanarttvFanarts.fill(tvSerie);
            }
        }
    }

    public File getImage(String path) {
        File result = null;
        
        if (isActive()) {
            result = Client.create().resource(path).get(File.class);
        }
        
        return result;
    }

}
