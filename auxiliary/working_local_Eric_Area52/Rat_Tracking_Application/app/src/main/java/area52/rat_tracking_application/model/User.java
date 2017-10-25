package area52.rat_tracking_application.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

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




        /** 5 possible boroughs of residency the user may select from using corresponding report
         * entry spinner
         * */
        public static List<String> boroughsOfResidency = Arrays.asList("QUEENS", "BRONX", "BROOKLYN", "HARLEM", "MANHATTAN", "STATEN ISLAND");

        /** allow us to assign unique id numbers to each user */
        private static int Next_Id = 0;

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
        public String getName() { return _name; }
        public void setName(String name) { _name = name; }

        //Will be a read only field
        public int getId() { return _id; }

        public void setBorough(String _borough) {
            this._borough = _borough;
        }

        public String getBorough() {return _borough; }

        /**
         * Lookup a borough based on its code.  Returns the position of that
         * borough in the array
         *
         * @param code the borough to find
         *
         * @return the index of the array that corresponds to the submitted borough
         */
        public static int findPosition(String code) {
            int i = 0;
            while (i < boroughsOfResidency.size()) {
                if (code.equals(boroughsOfResidency.get(i))) return i;
                ++i;
            }
            return 0;
        }


        /**
         * Make a new student
         * @param name      the student's name
         * @param major     the student's major
         */
        public User(String name, String major) {
            _name = name;
            _borough = major;
            _id = User.Next_Id++;
        }

        /**
         *
         * @return the display string representation
         */
        @Override
        public String toString() {
            return uName + " " + _borough;
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