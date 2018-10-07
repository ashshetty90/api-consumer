package core;

/**
 * @author ashish
 * either pass URL or multiple fetch related params based on the requirement.
 * Depending on the type of use case change the number of parameters
 */
public interface GenerateUserData {
    public void fetchUserData(String ...args);
}
