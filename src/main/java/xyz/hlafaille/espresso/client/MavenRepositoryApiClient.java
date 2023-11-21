package xyz.hlafaille.espresso.client;

import xyz.hlafaille.espresso.util.rest.RestClientFactory;
import xyz.hlafaille.espresso.util.rest.RestGetRequest;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic client for the Maven Repository API
 */
public class MavenRepositoryApiClient {
    private final RestClientFactory restClientFactory = new RestClientFactory("https://search.maven.org/solrsearch");

    public void search(String searchTerm) throws MalformedURLException {
        // build out request
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("q", "a:" + searchTerm);
        queryParameters.put("rows", "20");
        queryParameters.put("wt", "json");
        restClientFactory.buildGetRequest("/search", queryParameters, null);

        // send the request
    }
}
