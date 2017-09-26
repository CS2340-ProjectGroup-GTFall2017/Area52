package area52.rat_tracking_application.model;

/**
 * Created by Eric on 9/25/2017.
 */

import area52.rat_tracking_application.controllers.RegistrationActivity;
import android.support.compat.BuildConfig;

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

public class Model {
    /**
     * Singleton instance
     */
    private static final Model _instance = new Model();

    public static Model getInstance() {
        return _instance;
    }

    /**
     * holds the list of all Users
     */
    private User[] _users;

    /**
     * the currently logged in user
     */
    private User _currentUser;
    private RegistrationActivity registrationInstance;

    /**
     * Null Object pattern, returned when no user is found
     */
    private final User theNullUser = new User("No Such User");


    /**
     * make a new model
     */
    private Model() {
        _users = new User[5];
        //comment this out after full app developed
        loadTestData();
    }

    /**
     * populate the model with some dummy data.  The full app would not require this.
     * comment out when adding new courses functionality is present.
     */
    public void loadTestData() {
        _users[0] = new User("Eric", "eric", "numbers@1");
        _users[1] = new User("Jake", "jake", "numbers@2");
        _users[2] = new User("Heejoo", "heejoo", "numbers@3");
        _users[3] = new User("Grace", "grace", "numbers@4");
        _users[4] = new User("Randall", "randy", "numbers@5");
    }

    /**
     * get the users
     *
     * @return a list of the users registered to use the app
     */
    public User[] getUsers() {
        return _users;
    }

    /**
     * @return the currently logged in user
     */
    public User getCurrentUser() {
        return _currentUser;
    }

    public void setCurrentUser(User user) {
        _currentUser = user;
    }
}