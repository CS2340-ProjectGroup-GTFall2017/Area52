package area52.rat_tracking_application.model;

/**
 * Class representing an Admin, extends User class when instantiated.
 * Created by Eric on 9/24/2017.
 */

public class Admin extends User {

    /**
     * Updated with params by Jake Deerin on 10/31/2017:
     *
     * Creates a new Admin object
     * @param uName username of admin
     * @param email email of Admin
     * @param pWord password of Admin
     */
    public Admin(String uName, String email, String pWord) {
        super(uName, email, pWord);
    }

    public Admin() {
        this("", "", "");
    }


}
