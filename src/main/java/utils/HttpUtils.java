package utils;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author shas7002
 */
/*
* Utility class for all the Http requests and response*/
public class HttpUtils {
    static final Logger LOGGER = Logger.getLogger(HttpUtils.class);
    public String getResponseFromApi(String url) {
        LOGGER.info("Fetching user data from request URL");
        String response = "";
        try (CloseableHttpClient http = HttpClients.createDefault()) {
            final String request_url = url;
            final HttpGet get = new HttpGet(request_url);
            try (final CloseableHttpResponse closeableHttpResponse = http.execute(get)) {
                response = EntityUtils.toString(closeableHttpResponse.getEntity());
            }
        } catch (IOException e) {
            LOGGER.error("IO Exception",e);
        }
        LOGGER.info("User Data Fetched Successfully");
        return response;
    }
}
