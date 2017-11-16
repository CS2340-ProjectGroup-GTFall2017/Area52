package area52.rat_tracking_application.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static area52.rat_tracking_application.model.PersistenceManager.getPersistManagerInstance;

/**
 * Singleton created to function as interface between controllers and model
 * package classes.
 *
 * Updated with Serializable implementation by Jake Deerin on 10/31/2017
 *
 * Created by Eric on 10/11/2017.
 */

public class Model  implements Serializable {
    public static Model model = new Model();

    private List<User> users = new ArrayList<>();

    private HashMap<String, User> _users = new HashMap<>();

    /**
     * return static singleton instance of model for app data retrieval
     * @return model instance of model
     */
    public static Model getInstance() {
        return model;
    }

    /**
     * Method added by Jake Deerin on 10/31/2017
     *
     * @param newUser the newly registered user being added
     */
    public void addNewUser(User newUser) {
        users.add(newUser);
        syncUsersListAndHashMap();
    }

    /**
     * populate model with data to test app.
     */
    public void loadTestData() {
        _users.put("eric", new User("eric", "eric", "numbers@1"));
        _users.put("jake", new User("jake", "jake", "numbers@2"));
        _users.put("heejoo", new User("heejoo", "heejoo", "numbers@3"));
        _users.put("grace", new User("grace", "grace", "numbers@4"));
        _users.put("randy", new User("randy", "randy", "numbers@5"));
    }

    /**
     * Method added by Jake Deerin on 10/31/2017
     *
     *
     */
    public void syncUsersListAndHashMap() {
        for (User user : users) {
            _users.put(user.getUName(), user);
        }
    }

    /**
     * Method added by Jake Deerin on 10/31/2017
     *
     * @param usersBinary the saved file users.bin
     *
     * @return success boolean true if binary serialization
     * is successfully implemented, for synchronization of current
     * state with the state previously saved in persistance manager.
     * Otherwise, returns false.
     */
    public boolean loadUsers(File usersBinary) {

        boolean success = false;
        Object fromBinary = getPersistManagerInstance().loadBinary(usersBinary);

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
     * @return _users a HashMap of the users registered to use the app
     */
    public HashMap<String, User> getUserMap() {
        return _users;
    }
}
