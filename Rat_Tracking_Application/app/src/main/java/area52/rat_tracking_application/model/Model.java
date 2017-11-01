package area52.rat_tracking_application.model;

import java.io.File;
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
    public static Model model = new Model();

    private List<User> users = new ArrayList<>();
    private static HashMap<String, User> _users = new HashMap<>();

    public Model() {
        loadTestData();
    }

    /**
     * return static singleton instance of model for app data retrieval
     * @return
     */
    public static Model getInstance() {
        return model;
    }
    /**
     * populate model with data to test app if no users are found.
     */
    private void loadTestData() {
        if (users.size() == 0) {
            //the most secure password
            users.add(new Admin("admin", "admin", "password"));
        }
    }

    private void syncUsersListAndHashMap() {
        for (User user : users) {
            _users.put(user.getUName(), user);
        }
    }

    public boolean loadUsers(File usersBinary) {
        boolean success = false;
        Object fromBinary = PersistenceManager.getInstance().loadBinary(usersBinary);

        if (fromBinary != null) {
            model = (Model) fromBinary;
            model.syncUsersListAndHashMap();
            success = true;
        }
        return success;
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
