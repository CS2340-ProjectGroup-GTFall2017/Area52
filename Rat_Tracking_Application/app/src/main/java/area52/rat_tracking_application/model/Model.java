package area52.rat_tracking_application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Singleton created to function as interface between controllers and model
 * package classes.
 * Created by Eric on 10/11/2017.
 */

public class Model implements Serializable {
    public static final Model model = new Model();

    private List<User> users = new ArrayList<>();
    private static HashMap<String, User> _users = new HashMap<>();

    /**
     * return static singleton instance of model for app data retrieval
     * @return
     */
    public static Model getInstance() {
        loadTestData();
        return model;
    }
    /**
     * populate model with data to test app if no users are found.
     */
    private static void loadTestData() {
        if (_users.keySet().size() == 0) {
            _users.put("eric", new User("eric", "eric", "numbers@1"));
            _users.put("jake", new User("jake", "jake", "numbers@2"));
            _users.put("heejoo", new User("heejoo", "heejoo", "numbers@3"));
            _users.put("grace", new User("grace", "grace", "numbers@4"));
            _users.put("randy", new User("randy", "randy", "numbers@5"));
        }
    }

    /**
     *
     *
     * @return a HashMap of the users registered to use the app
     */
    public HashMap<String, User> getUserMap() {
        return _users;
    }
}
