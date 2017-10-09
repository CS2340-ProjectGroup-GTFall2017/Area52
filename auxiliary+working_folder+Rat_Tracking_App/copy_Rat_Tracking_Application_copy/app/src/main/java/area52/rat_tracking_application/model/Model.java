package area52.rat_tracking_application.model;

/**
 * Created by Eric on 9/25/2017.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import area52.rat_tracking_application.controllers.RegistrationActivity;

/**
 * Created by robert waters on 1/5/17.
 *
 * modified for Area52 M4 submission for emulator testing of project requirements.
 *
 * This is our facade to the Model.  We are using a Singleton design pattern to allow
 * access to the model from each controller.
 *
 *
 */

public class Model<T> {
    /**
     * Singleton instance
     */
    private static final Model _instance_ = new Model();

    public static Model getInstance() {
        return _instance_;
    }

    /**
     * holds the list of all Users
     */
    private List<T> names = new LinkedList<>();
    private HashMap<T, List<T>> userHashMap = new LinkedHashMap<>();
    private List<List<T>> userList = new LinkedList<>();

    /**
     * the currently logged in user
     */
    private User<T> currentUser;

    /**
     * make a new model
     */
    /*private Model() {
        loadTestData();
    }*/

    /**
     * populate model with data to test app.
     */
    /*public void loadTestData() {
        userHashMap.put("eric", new User("Eric", "eric", "numbers@1"));
        userHashMap.put("jake", new User("Jake", "jake", "numbers@2"));
        userHashMap.put("heejoo", new User("Heejoo", "heejoo", "numbers@3"));
        userHashMap.put("grace", new User("Grace", "grace", "numbers@4"));
        userHashMap.put("randy", new User("Randall", "randy", "numbers@5"));
    }*/
    public List<T> getUserNames() {
        return new LinkedList<>(userHashMap.keySet());
    }

    public HashMap<T, List<T>> addUserToLinkedHashMap(User<T> newRegisteredUser, List<T> userInfo) {
        currentUser = newRegisteredUser;
        userInfo.clear();
        userInfo.add(currentUser.getUName());
        userInfo.add(currentUser.getEmail());
        userInfo.add(currentUser.getPWord());
        userList.add(userInfo);
        userHashMap.put(currentUser.getUName(), userInfo);
        return userHashMap;
    }

    /**
     * @return the currently logged in user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }
}