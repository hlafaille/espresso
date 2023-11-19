package xyz.hlafaille.espresso.util.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Base client for retrieving data from a REST API
 */
public class RestClientFactory {
    private String baseUrl;

    public RestClientFactory(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Build a RestGetRequest object
     * @param endpoint API Endpoint (ex: /posts/12345/comments)
     * @param queryParameters Additional
     * @param additionalHeaders
     * @return
     */
    public RestGetRequest buildGetRequest(String endpoint, Map<String, String> queryParameters, Map<String, String> additionalHeaders) throws MalformedURLException {
        return new RestGetRequest(
          new URL(baseUrl + endpoint),
                queryParameters,
                additionalHeaders
        );
    }
}
