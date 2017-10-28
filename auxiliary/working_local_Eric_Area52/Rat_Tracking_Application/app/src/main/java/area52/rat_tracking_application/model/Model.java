package area52.rat_tracking_application.model;

import java.util.HashMap;

/**
 * Singleton created to function as interface between controllers and model
 * package classes.
 * Created by Eric on 10/11/2017.
 */

public class Model {
    public static final Model model = new Model();
    public static String ratReport;

    private static HashMap<String, User> _users = new HashMap<>();

    /**
     * return static singleton instance of model for app data retrieval
     * @return
     */
    public static Model getInstance() {
        loadTestData();
        return model;
    }
    /**
     * populate model with data to test app.
     */
    private static void loadTestData() {
        _users.put("eric", new User("eric", "eric", "numbers@1"));
        _users.put("jake", new User("jake", "jake", "numbers@2"));
        _users.put("heejoo", new User("heejoo", "heejoo", "numbers@3"));
        _users.put("grace", new User("grace", "grace", "numbers@4"));
        _users.put("randy", new User("randy", "randy", "numbers@5"));
    }

    public void setCurrentReport(String ratReport) {
        this.ratReport = ratReport;
    }

    public String getCurrentReport() {
        return ratReport;
    }

    /**
     *
     *
     * @return a HashMap of the users registered to use the app
     */
    public HashMap<String, User> getUserMap() {
        return _users;
    }
}
