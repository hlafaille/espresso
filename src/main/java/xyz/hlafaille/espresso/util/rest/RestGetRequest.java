package xyz.hlafaille.espresso.util.rest;

import com.google.gson.Gson;
import xyz.hlafaille.espresso.exception.EspressoRestClientException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class RestGetRequest {
    private final URL fullUrl;
    private final Map<String, String> queryParameters;
    private final Map<String, String> additionalHeaders;

    public RestGetRequest(URL fullUrl, Map<String, String> queryParameters, Map<String, String> additionalHeaders) {
        this.fullUrl = fullUrl;
        this.queryParameters = queryParameters;
        this.additionalHeaders = additionalHeaders;
    }

    /**
     * Call the specified REST endpoint
     *
     * @param responseType Subclass of RestResponse matching the endpoints response schema
     */
    public <T extends RestResponse> T call(Class<T> responseType) throws IOException, EspressoRestClientException {
        // pre-request setup
        HttpsURLConnection connection = (HttpsURLConnection) fullUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        // if response code is not 200 or 201, throw EspressoRestClientException
        if (connection.getResponseCode() != 200 || connection.getResponseCode() != 201) {
            throw new EspressoRestClientException("Todo finish", connection.getResponseCode());
        }

        // handle our response
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        // generate our response
        String response;
        while ((response = bufferedReader.readLine()) != null) {
            connection.disconnect();
        }

        // parse the response into the provided response container
        return new Gson().fromJson(response, responseType);
    }
}
