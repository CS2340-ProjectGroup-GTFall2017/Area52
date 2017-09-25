package area52.rat_tracking_application.model;

/**
 * Created by Eric on 9/24/2017.
 */

public abstract class User {
    private String name;
    public User(String name) {
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

