package area52.rat_tracking_application.model;

import area52.rat_tracking_application.controllers.RegistrationActivity;

/**
 * Created by Eric on 9/24/2017.
 */

public class User {
    private String email;
    private String pWord;
    private String uName;
    private RegistrationActivity regAct;

    /**
     * no argument constructor creates instance of
     * RegistrationActivity class.
     */
    public User() {
        regAct = new RegistrationActivity();
    }

    /**
     *
     * @param uName username of user instance
     * @param email email of user instance
     * @param pWord password of user instance
     */
    public User(String uName, String email, String pWord) {
        this.uName = uName;
        this.email = email;
        this.pWord = pWord;
    }

    /**
     *
     * @return registration activity instance
     */
    public RegistrationActivity getRegAct() {
        return regAct;
    }

    /**
     *
     * @param uName username of user instance
     */
    public void setUName(String uName) {
        uName = regAct.getUsername();
    }

    /**
     *
     * @return uName username of user instance
     */
    public String getUName() {
        return uName;
    }

    /**
     *
     * @param email email of user instance
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return email of user instance
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param pWord password of user instance
     */
    public void setPWord(String pWord) {
        this.pWord = pWord;
    }

    /**
     *
     * @return password of user instance
     */
    public String getPWord() {
        return pWord;
    }
}