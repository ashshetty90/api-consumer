package core;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import exception.HttpResponseException;
import org.apache.log4j.Logger;
import utils.JsonUtils;
import utils.HttpUtils;

import java.util.*;
import java.util.function.Function;
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
        List<UserInfo> enrichedUserData = new ArrayList<UserInfo>();
        for (UserInfo data : userData) {
            Long uId = data.getUser_id();
            String created_at = data.getCreated_at();
            String ip = data.getIp();
            HttpUtils httpUtils = new HttpUtils();
            LOGGER.info("Fetching user status for client id " + uId);
            Map<String, String> cityJson = null;
            try {
                Map<String, String> userStatusJson = gson.fromJson(httpUtils.getResponseFromApi(String.format(userStatusWebAddr, uId, created_at)), Map.class);
                data.setStatus(userStatusJson.get("user_status"));
                LOGGER.info("User status for client id " + uId + " fetched successfully");
                LOGGER.info("Fetching country name for ip " + ip);
                cityJson = gson.fromJson(httpUtils.getResponseFromApi(String.format(cityIpWebAddr, ip)), Map.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            LOGGER.info("Country name for ip " + ip + " fetched successfully");
            data.setCountry(cityJson.get("city"));
            enrichedUserData.add(data);
        }
        LOGGER.info("Writing enriched user data to output file location");
        jsonUtils.writeJsonResponse(enrichedUserData, outputFilePath);
        LOGGER.info("File writing completed successfully");
    }

    public static void calculateAggregateCounts(String inputPath,String outPath) {
        JsonUtils jsonUtils = new JsonUtils();
        List<UserInfo> userData = jsonUtils.readJson(inputPath);
        Map<String, Map<String, Double>> aggregateUserData = userData.stream().collect(
                Collectors.groupingBy(
                        UserInfo::getStatus, Collectors.groupingBy(
                                UserInfo::getCountry, Collectors.summingDouble(
                                        UserInfo::getProduct_price))));

       jsonUtils.writeJsonResponse(new Gson().toJson(aggregateUserData),outPath);
    }

}
