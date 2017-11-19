package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.File;
import java.util.HashMap;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Admin;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.PersistenceManager;
import area52.rat_tracking_application.model.User;

import static area52.rat_tracking_application.model.PersistenceManager.getPersistManagerInstance;

public class RegistrationActivity extends AppCompatActivity {
    EditText usernameText;
    EditText emailText;
    EditText passwordText;
    CheckBox adminCheckbox;

    Button registerButton;
    Button cancelButton;

    private String username;
    private String email;
    private String password;

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

    public void registerUser(View view) {
        username = usernameText.getText().toString();
        setUsername(username);
        email = emailText.getText().toString();
        setEmail(email);
        password = passwordText.getText().toString();
        setPassword(password);
        boolean isAdmin = adminCheckbox.isChecked();

        if (username != null && email != null && password != null) {
            User newUser;
            if (isAdmin) {
                newUser = new Admin();
                newUser.setUName(getUsername());
                newUser.setEmail(getEmail());
                newUser.setPWord(getPassword());
                HashMap<String, User> userMap = Model.model.getUserMap();//.put(getUsername(), newUser);
                if (!userMap.containsKey(getUsername())){
                    Model.model.getUserMap().put(getUsername(), newUser);
                    Model.getInstance().addNewUser(newUser);
                    saveNewUser();
                    Context reportDetailContext = view.getContext();
                    Intent reportDetailIntent = new Intent(reportDetailContext, ReportDetailActivity.class);
                    reportDetailContext.startActivity(reportDetailIntent);
                } else {
                    goBackToLoginScreen(view);
                }

            } else {
                newUser = new User();
                newUser.setUName(getUsername());
                newUser.setEmail(getEmail());
                newUser.setPWord(getPassword());
                HashMap<String, User> userMap = Model.model.getUserMap();
                if (!userMap.containsKey(getUsername())){
                    Model.model.getUserMap().put(getUsername(), newUser);
                    Model.getInstance().addNewUser(newUser);
                    saveNewUser();
                    Context mainContext = view.getContext();
                    Intent mainIntent = new Intent(mainContext, MainActivity.class);
                    mainContext.startActivity(mainIntent);
                } else {
                    goBackToLoginScreen(view);
                }

            }
        }
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void goBackToLoginScreen(View view) {
        Intent loginScreenActivity = new Intent(this, MainActivity.class);
        startActivity(loginScreenActivity);
    }
    /**
     * Save rat reports as a binary file
     *
     * Should be changed to onClose() saveRatReports() && onLogout() saveRatReports()
     */
    private void saveNewUser() {
        File usersFile = new File(this.getFilesDir(), PersistenceManager.USERS_DATA_FILENAME);
        getPersistManagerInstance().saveBinary(usersFile, Model.getInstance());
    }
}
