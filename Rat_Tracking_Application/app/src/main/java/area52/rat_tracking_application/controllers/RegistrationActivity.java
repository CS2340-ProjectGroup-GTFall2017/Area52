package area52.rat_tracking_application.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import area52.rat_tracking_application.R;

public class RegistrationActivity extends AppCompatActivity {
    EditText usernameText;
    EditText emailText;
    EditText passwordText;
    CheckBox adminCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameText = (EditText) findViewById(R.id.editText_username);
        emailText = (EditText) findViewById(R.id.editText_email);
        passwordText = (EditText) findViewById(R.id.editText_password);
        adminCheckbox = (CheckBox) findViewById(R.id.checkBox_admin);
    }
}
