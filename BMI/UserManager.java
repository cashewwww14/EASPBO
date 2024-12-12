// UserManager.java
import java.util.HashMap;

public class UserManager {
    private HashMap<String, RegularUser> users = new HashMap<>();

    public boolean registerUser(String username, int age, double height, double weight) {
        if (users.containsKey(username)) {
            return false; // Username already exists
        }
        users.put(username, new RegularUser(username, age, height, weight));
        return true;
    }

    public RegularUser loginUser(String username) {
        return users.get(username);
    }
}
