package core;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;
import utils.JsonUtils;
import utils.HttpUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ashish
 */

/**
 * GenerateUserDataImpl class is the class where all the computing logic lies
 * It is created as a concerete implementation of GenerateUserData interface.
 */

public class GenerateUserDataImpl implements GenerateUserData {
    static final Logger LOGGER = Logger.getLogger(GenerateUserDataImpl.class);

    /*
    * fetchUserData method fetches user data
    * based on the inputs provided via command line
    * 1st argument is the inputFilePath variable which holds the location from where the json file has to be read
    * 2nd argument is the outputFilePath where the result needs to be written
    * 3rd argument 'userStatusWebAddr' is the Web Address for fetching user status
    * 4th argument 'cityIpWebAddr' is the web address for fetching city data
    * */
    public void fetchUserData(String[] args) {
        String inputFilePath = args[0];
        String outputFilePath = args[1];
        String userStatusWebAddr = args[2];
        String cityIpWebAddr = args[3];
        /*
        * Gson library is used below to minimize hardcoding
        * in getRequests
        * The request json is directly mapped to the POJO file created.
        * Any new entry in the request just needs an entry in the POJO class
        * */
        Gson gson = new Gson();
        JsonUtils jsonUtils = new JsonUtils();

        List<UserInfo> userData = jsonUtils.readJson(inputFilePath);

        List<UserInfo> enrichedUserData = getEnrichedData(userStatusWebAddr, cityIpWebAddr, gson, userData);

        LOGGER.info("Writing enriched user data to output file location");

        jsonUtils.writeJsonResponse(enrichedUserData, outputFilePath);

        LOGGER.info("File writing completed successfully");
    }

    private List<UserInfo> getEnrichedData(String userStatusWebAddr, String cityIpWebAddr, Gson gson, List<UserInfo> userData) {
        List<UserInfo> enrichedUserData = new ArrayList<UserInfo>();
        for (UserInfo data : userData) {
            HttpUtils httpUtils = new HttpUtils();
            LOGGER.info("Fetching user status for client user id ");
            Map<String, String> cityJson = new HashMap<String ,String>();

            String userStatusUrl = HttpUtils.prepareUserUrl(userStatusWebAddr, data.getUser_id(), data.getCreated_at());
            String cityUrl = HttpUtils.prepareCityUrl(cityIpWebAddr, data.getIp());

            try {
                Map<String, String> userStatusJson = httpUtils.getResponseFromApi(gson, httpUtils, userStatusUrl);
                data.setStatus(userStatusJson.get("user_status"));

                LOGGER.info("User status for client id fetched successfully");
                LOGGER.info("Fetching country name for ip ");

                cityJson = httpUtils.getResponseFromApi(gson, httpUtils, cityUrl);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            LOGGER.info("Country name for ip  fetched successfully");

            data.setCountry(cityJson.get("city"));
            enrichedUserData.add(data);
        }
        return enrichedUserData;
    }


    public static void calculateAggregateCounts(String inputPath, String outPath) {
        JsonUtils jsonUtils = new JsonUtils();
        List<UserInfo> userData = jsonUtils.readJson(inputPath);
        Map<String, Map<String, Double>> aggregateUserData = userData.stream().collect(
                Collectors.groupingBy(
                        UserInfo::getStatus, Collectors.groupingBy(
                                UserInfo::getCountry, Collectors.summingDouble(
                                        UserInfo::getProduct_price))));

        jsonUtils.writeJsonResponse(new Gson().toJson(aggregateUserData), outPath);
    }

}
