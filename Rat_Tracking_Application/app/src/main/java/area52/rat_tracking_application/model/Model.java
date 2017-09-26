package area52.rat_tracking_application.model;

/**
 * Created by Eric on 9/25/2017.
 */

import area52.rat_tracking_application.controllers.RegistrationActivity;
import android.support.compat.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;
import android.support.compat.BuildConfig;

import java.util.ArrayList;
import java.util.List;


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
    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    /** holds the list of all Users */
    private List<User> _users;

    /** the currently logged in user */
    private User _currentUser;
    private RegistrationActivity registrationInstance;

    /** Null Object pattern, returned when no user is found */
    private final User theNullUser = new User("No Such User");


    /**
     * make a new model
     */
    private Model() {
        _users = new ArrayList<>();

        //comment this out after full app developed -- for homework leave in
        loadDummyData();

    }

    /**
     * populate the model with some dummy data.  The full app would not require this.
     * comment out when adding new courses functionality is present.
     */
    public void loadDummyData() {
        _users.add(new User("Eric"));
        _users.add(new User( "Jake"));
        _users.add(new User("Heejoo"));
        _users.add(new User("Grace"));
        _users.add(new User("Randall"));
    }

    /**
     * get the users
     * @return a list of the users registered to use the app
     */
    public List<User> getUsers() { return _users; }

    /**
     * add a user to the app.  checks if the user has already registered.
     *
     * uses O(n) linear search for user
     *
     * @param user  the user to be added
     * @return true if added, false if a duplicate
     */
    public boolean checkUserExists(User user) {
        for (User u : _users ) {
            if (u.equals(user)) return false;
        }
        _users.add(user);
        return true;
    }

    /**
     *
     * @return  the currently logged in user
     */
    public User getCurrentUser() { return _currentUser;}

    public void setCurrentUser(User user) { _currentUser = user; }

    /**
     * Return a user that has matching username and password.
     * This uses an O(n) linear search.
     *
     * @param userName the username input by the user logging in
     * @param password the password input by the user logging in
     * @return  the user with the matching userName and password or NullUser if no such user exists.
     *
     */
    public User showLogInConfirmation(String userName, String password) {
            if (_currentUser.getPWord().equals(password) && _currentUser.getUName().equals(userName)) {
                return _currentUser;
            }
            return theNullUser;
    }

    /**
     * add a new user to the app db (db currently inactive)
     *
     * @param user the user to add
     * @return true if user added, false if not added
     */
    public boolean addNew(User user) {
        if (registrationInstance.validUsername() && registrationInstance.validPassword()) {
            registrationInstance.addNewUser();
            return true;
        }
        return false;
    }

    /**
     * Add new user data
     *
     * @param user the user being edited
     */
    public void replaceUserData(User user) {
        User existing = showLogInConfirmation(user.getUName(), user.getPWord());

        //if existing comes back null, something is seriously wrong
        if (BuildConfig.DEBUG && (existing == null)) { throw new AssertionError(); }

        //update the userName
        existing.setUName(user.getUName());

        //update the password
        existing.setPWord(user.getPWord());
    }
}

