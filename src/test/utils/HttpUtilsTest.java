package utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author shas7002
 */
public class HttpUtilsTest {
    @Test
    public void getResponseFromApi() throws Exception {
        Assert.assertEquals("{\"user_status\":\"non-paying\"}",new HttpUtils().getResponseFromApi("http://127.0.0.1:5000/user_status/1?date=2017-10-10T10:00:00"));
    }

}