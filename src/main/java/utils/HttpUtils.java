package utils;

import com.google.gson.Gson;
import exception.HttpResponseException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * @author ashish
 */
/*
* Utility class for all the Http requests and response*/
public class HttpUtils {
    static final Logger LOGGER = Logger.getLogger(HttpUtils.class);
    public static final String HTTP_RESPONSE_MESSAGE = "Non 200 response received. Please try after some time. Status code is ";
    public String getResponseFromApi(String url) {
        LOGGER.info("Fetching user data from request URL");
        String response = "";
        try (CloseableHttpClient http = HttpClients.createDefault()) {
            final String request_url = url;
            final HttpGet get = new HttpGet(request_url);
            try (final CloseableHttpResponse closeableHttpResponse = http.execute(get)) {
                if (closeableHttpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    throw new HttpResponseException(HTTP_RESPONSE_MESSAGE+closeableHttpResponse.getStatusLine().getStatusCode());
                }
                response = EntityUtils.toString(closeableHttpResponse.getEntity());
            }
        } catch (IOException e) {
            LOGGER.error("IO Exception",e);
        }

        LOGGER.info("User Data Fetched Successfully");
        return response;
    }

    public static String prepareCityUrl(String cityIpWebAddr, String ip) {
        return String.format(cityIpWebAddr,ip);
    }

    public static String prepareUserUrl(String userStatusWebAddr, Long uId, String created_at) {
        return String.format(userStatusWebAddr, uId, created_at);
    }

    public Map<String,String > getResponseFromApi(Gson gson, HttpUtils httpUtils, String userStatusUrl) {
        return gson.fromJson(httpUtils.getResponseFromApi(userStatusUrl), Map.class);
    }

}
