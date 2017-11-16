package area52.rat_tracking_application.model;

import java.io.Serializable;

import area52.rat_tracking_application.controllers.RegistrationActivity;

/**
 * Template:
 * Created by robertwaters on 1/5/17.
 *
 * Information Holder - represents a single student in model
 *
 * We are passing this object in a bundle between intents, so we implement
 * the Parcelable interface.
 *
 */
public class User implements Serializable {
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


        /** this user id number, which is used in place of username by admin*/
        private int _id;

        /** this user's name */
        private String _name;

        private String _borough;

        /** this */
        private boolean nycResidentStatus;


        /* **********************
         * Getters and setters
         */

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

    /**
     * @return true of user is a resident of NYC
     */
    public boolean getNYCResidencyStatus() {
        return nycResidentStatus;
    }

    /**
     * @param
     */
    public void setNYCResidencyStatus(boolean nycResidencyStatus) {
        this.nycResidentStatus = nycResidencyStatus;

    }
}