package area52.rat_tracking_application.model;
import android.view.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static area52.rat_tracking_application.R.raw.rat_sightings;

/**
 * Singleton created to function as interface between controllers and model
 * package classes.
 * Created by Eric on 10/11/2017.
 */

public class Model {
    public static final Model model = new Model();

    private static HashMap<String, User> _users = new HashMap<>();
    private static RatReportLoader loader = new RatReportLoader();
    private RatReport report;

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

    /**
     *
     *
     * @return a HashMap of the users registered to use the app
     */
    public HashMap<String, User> getUserMap() {
        return _users;
    }
    public static HashMap<Long, RatReport> getRatReports() {
        return loader.reports;
    }
    public static List<RatReport> getUniqueIDsAsList() {
        return (LinkedList) loader.reports.keySet();
    }
    public void setCurrentReport(RatReport report) {
        this.report = report;
    }
    public View getCurrentReport(View mView) {
        return mView;
    }

}
