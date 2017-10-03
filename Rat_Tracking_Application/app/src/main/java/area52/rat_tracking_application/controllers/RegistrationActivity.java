package area52.rat_tracking_application.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.User;

public class RegistrationActivity extends AppCompatActivity {
    EditText usernameText;
    EditText emailText;
    EditText passwordText;
    CheckBox adminCheckbox;

    Button registerButton;
    Button cancelButton;

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
        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        boolean isAdmin = adminCheckbox.isChecked();

        if (username != null && email != null && password != null) {
            User newlyRegisteredUser = new User(username, username, password);
            Model.getInstance().addUser(newlyRegisteredUser);
            goBackToLoginScreen(view);
        }
    }

    public void goBackToLoginScreen(View view) {
        Intent loginScreenActivity = new Intent(this, MainActivity.class);
        startActivity(loginScreenActivity);
    }
}
