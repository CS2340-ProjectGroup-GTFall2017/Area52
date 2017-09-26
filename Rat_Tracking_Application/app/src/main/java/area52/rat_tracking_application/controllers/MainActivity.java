package area52.rat_tracking_application.controllers;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.User;

public class MainActivity extends Activity  {
    Button buttonOne;
    Button buttonTwo;
    EditText editOne;
    EditText editTwo;
    TextView textViewOne;
    int securityCounter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOne = (Button)findViewById(R.id.button);
        buttonTwo = (Button)findViewById(R.id.button2);
        editOne = (EditText)findViewById(R.id.editText);
        editTwo = (EditText)findViewById(R.id.editText2);
        textViewOne = (TextView)findViewById(R.id.textView3);
        textViewOne.setVisibility(View.GONE);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editOne.getText().toString().equals("user") &&
                        editTwo.getText().toString().equals("pass")) {
                    Toast.makeText(getApplicationContext(),
                            "Welcome to the NYC Rat Tracking System!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Incorrect Login Info"
                            ,Toast.LENGTH_SHORT).show();
                    textViewOne.setVisibility(View.VISIBLE);
                    textViewOne.setBackgroundColor(Color.RED);
                    securityCounter--;
                    textViewOne.setText(Integer.toString(securityCounter));
                    if (securityCounter == 0) {
                        buttonOne.setEnabled(false);
                    }
                }
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    /**protected Boolean doInBackground(User user) {
     Model.getInstance().setCurrentUser(user);
     for (User u : Model.getInstance().getUsers()) {
     if (u.equals(Model.getInstance().getCurrentUser())) {
     Model.getInstance().showLogInConfirmation(Model.getInstance().getCurrentUser(
     ).getUName(), Model.getInstance().getCurrentUser().getPWord());
     return true;
     }
     }
     return false;
     }**/
}