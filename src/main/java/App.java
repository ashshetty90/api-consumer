import core.GenerateUserData;
import core.GenerateUserDataImpl;

/**
 * @author shas7002
 */
public class App {
    /*
    * Entry point of the application. Object of class GenerateUserDataImpl
    * created to fetch user data
    * */
    public static void main(String[] args) {
        GenerateUserData generateUserData = new GenerateUserDataImpl();
        generateUserData.fetchUserData(args);
        GenerateUserDataImpl.calculateAggregateCounts(args[1],args[4]);
    }


}
