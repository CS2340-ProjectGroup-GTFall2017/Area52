package area52.rat_tracking_application.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.HashMap;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.User;
/*
*@Author randi
* Gived the user their password given username and email
 */
public class EmailReset extends AppCompatActivity {
    EditText usernameT;
    EditText emailT;
    Button checkButton;
    Button cancelButton;

    private String username;
    private String email;
    private String password;
    private User checkUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_reset);

        usernameT = (EditText) findViewById(R.id.editText_usernameReset);
        emailT = (EditText) findViewById(R.id.editText_emailReset);
        checkButton = (Button) findViewById(R.id.button_getPW);
        cancelButton = (Button) findViewById(R.id.button_cancel);

    }

    public void checkUser(View view) {
        username = usernameT.getText().toString();
        email = emailT.getText().toString();


        if (username != null && email != null) {
            HashMap<String, User> userMap = Model.model.getUserMap();
            if (userMap.containsKey(username)){
                checkUser = userMap.get(username);
                if (checkUser.getEmail().equals(email)) {
                    password = checkUser.getPWord();
                    Toast.makeText(
                            getApplicationContext(),
                            "Password is: " + password,
                            Toast.LENGTH_LONG).show();
                    backToLoginScreen(view);
                }else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Username and Email not valid",
                            Toast.LENGTH_LONG).show();
                    backToLoginScreen(view);
                }

            }else {
                Toast.makeText(
                        getApplicationContext(),
                        "Username and Email not valid",
                        Toast.LENGTH_LONG).show();
                backToLoginScreen(view);
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Username and Email not valid",
                    Toast.LENGTH_LONG).show();
            backToLoginScreen(view);
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

    public void backToLoginScreen(View view) {
        Intent loginScreenActivity = new Intent(this, MainActivity.class);
        startActivity(loginScreenActivity);
    }
}
