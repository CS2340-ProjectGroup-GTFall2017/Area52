package area52.rat_tracking_application.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.User;

public class RegistrationActivity<T> extends AppCompatActivity {
    private EditText usernameText;
    private EditText emailText;
    private EditText passwordText;
    private CheckBox adminCheckbox;

    private Button registerButton;
    private Button cancelButton;

    private User<T> newRegisteredUser = new User<>();
    private HashMap<T, List<T>> registeredUserMap = new LinkedHashMap<>();
    private List<List<T>> userProfileList = new LinkedList<>();

    /**
     * the currently logged in user
     */
    private User<T> currentUser = new User<>();

    private List<T> userProfile = new ArrayList<>();

    private List<User<T>> userList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameText = (EditText) findViewById(R.id.editText_username);
        emailText = (EditText) findViewById(R.id.editText_email);
        passwordText = (EditText) findViewById(R.id.editText_password);
        adminCheckbox = (CheckBox) findViewById(R.id.checkBox_admin);

        registerButton = (Button) findViewById(R.id.button_register);
        cancelButton = (Button) findViewById(R.id.button_cancel);
    }

    private void registerUser(View view) {
        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        Boolean isAdmin = adminCheckbox.isChecked();

        if (username != null && email != null && password != null) {
            newRegisteredUser.setUName((T) username);
            newRegisteredUser.setEmail((T) email);
            newRegisteredUser.setPWord((T) password);
            addToRegisteredUserMap(newRegisteredUser, userProfile);
            goBackToLoginScreen(view);
        }
    }
    public HashMap<T, List<T>> addToRegisteredUserMap(User<T> newRegisteredUser, List<T> userProfile) {
        userProfile.clear();
        currentUser = newRegisteredUser;
        getRegisteredUserMap().put(currentUser.getUName(), userProfile);
        return registeredUserMap;
    }

    public HashMap<T, List<T>> getRegisteredUserMap() {
        setUserProfile(currentUser, userProfile);
        return registeredUserMap;
    }

    public void setUserProfile(User<T> currentUser, List<T> userProfile){
        userProfile.clear();
        userProfile.add(currentUser.getUName());
        userProfile.add(currentUser.getEmail());
        userProfile.add(currentUser.getPWord());
    }

    public List<T> getUserProfile() {
        return userProfile;
    }

    public List<User<T>> getUsers() {
        return userList;
    }

    public List<List<T>> addUserProfileToList() {
        userProfileList.add(userProfile);
        return userProfileList;
    }

    public List<List<T>> getListOfAllMapValues() {
        return new LinkedList<>(registeredUserMap.values());
    }

    public List<List<T>> getAllUserProfiles() {
        return userProfileList;
    }

    public List<T> getUserNames() {
        return new LinkedList<>(registeredUserMap.keySet());
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public void goBackToLoginScreen(View view) {
        Intent loginScreenActivity = new Intent(this, MainActivity.class);
        startActivity(loginScreenActivity);
    }
}