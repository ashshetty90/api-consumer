package core;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author ashish
 */
public class GenerateUserDataImplTest {
    @Test
    public void calculateAggregateCounts() throws Exception {
        List<UserInfo>  userInfos = new ArrayList<UserInfo>();
        userInfos.add(new UserInfo(Long.valueOf(123),"2016-10-03T10:50:33",Long.valueOf(98373),Long.valueOf(1),"10.10.10.10","london","paying",Double.valueOf(220.5)));
        userInfos.add(new UserInfo(Long.valueOf(124),"2017-02-03T10:51:33",Long.valueOf(97373),Long.valueOf(1),"10.13.10.10","munich","paying",Double.valueOf(320.5)));
        userInfos.add(new UserInfo(Long.valueOf(125),"2017-10-03T10:52:33",Long.valueOf(96373),Long.valueOf(1),"192.168.2.1","**unknown**","cancelled",Double.valueOf(20.5)));
        userInfos.add(new UserInfo(Long.valueOf(126),"2017-10-03T11:50:33",Long.valueOf(88373),Long.valueOf(2),"172.16.11.10","'munich'","non-paying",Double.valueOf(220.5)));
        String testJsonResponse = "{\"paying\":{\"london\":220.5,\"munich\":320.5},\"non-paying\":{\"\\u0027munich\\u0027\":220.5},\"cancelled\":{\"**unknown**\":20.5}}";
        Assert.assertEquals(testJsonResponse,new Gson().toJson(userInfos.stream().collect(
                Collectors.groupingBy(
                        UserInfo::getStatus, Collectors.groupingBy(
                                UserInfo::getCountry, Collectors.summingDouble(
                                        UserInfo::getProduct_price))))));
    }

}