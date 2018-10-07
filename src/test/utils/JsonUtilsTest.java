package utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author shas7002
 */
public class JsonUtilsTest {
    @Test
    public void readJson() throws Exception {
        new JsonUtils().readJson("src/main/resources/transactions.json");
    }

}