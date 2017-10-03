package area52.rat_tracking_application.model;

import area52.rat_tracking_application.controllers.RegistrationActivity;

/**
 * Created by Eric on 9/24/2017.
 */

public class User {
    private String email;
    private String pWord;
    private String uName;
    private RegistrationActivity registrationActivity;

    public User() {
        registrationActivity = new RegistrationActivity();
    }

    public User(String uName, String email, String pWord) {
        this.uName = uName;
        this.email = email;
        this.pWord = pWord;
    }

    public RegistrationActivity getRegistrationActivity() {
        return registrationActivity;
    }

    public void setRegistrationActivity(RegistrationActivity registrationActivity) {
        this.registrationActivity = registrationActivity;
    }

    public void setUName() {
        uName = registrationActivity.getUName();
    }

    public String getUName() {
        return uName;
    }

    public void setEmail() {
        email = registrationActivity.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setPWord(String pWord) {
        pWord = registrationActivity.getPassword();
    }

    public String getPWord() {
        return pWord;
    }
}