package utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.UserInfo;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author shas7002
 */
/*
* Utility class created to read and write json data
* */
public class JsonUtils {
    /*
    * readJson method reads json file based on the input path provided
    * */
    static final Logger LOGGER = Logger.getLogger(JsonUtils.class);

    public List<UserInfo> readJson(String filePath) {
        List<UserInfo> inputData = null;
        try (FileReader fileReader = new FileReader(filePath)) {
            JSONParser jsonParser = new JSONParser();
            Object o = jsonParser.parse(fileReader);
            JSONArray userData = (JSONArray) o;
            Gson gson = new Gson();
            Type type = new TypeToken<List<UserInfo>>(){}.getType();
            inputData = gson.fromJson(userData.toJSONString(),type);
            return inputData;
        } catch (FileNotFoundException e) {
            LOGGER.error("File Not Found Exception",e);
        } catch (IOException e) {
            LOGGER.error("IO Exception",e);
        } catch (ParseException e) {
            LOGGER.error("Parse Exception",e);
        }
        return inputData;
    }
    /*
    * writeJsonResponse takes JsonString as an input and writes Json response to the output location
    * provided as a parameter
    * */
    public void writeJsonResponse(String aggregateUserData,String outputFilePath){
        try (FileWriter file = new FileWriter(outputFilePath)) {

            file.write(aggregateUserData);
            file.flush();

        } catch (IOException e) {
            LOGGER.error("Io Exception",e);
        }
    }
    /*
    * Overloaded writeJsonResponse takes a List of enrichedData instead
    * of JsonArray */
    public void writeJsonResponse(List<UserInfo> enrichedUserData, String outputFilePath){
        LOGGER.info("Writing json response to location "+outputFilePath);
        try (FileWriter file = new FileWriter(outputFilePath)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(enrichedUserData,file);

        } catch (IOException e) {
            LOGGER.error("Io Exception",e);
        }
    }
}
