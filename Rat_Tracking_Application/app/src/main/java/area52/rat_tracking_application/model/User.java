package area52.rat_tracking_application.model;

/**
 * Created by Eric on 9/24/2017.
 */

public class User {
    private String name;
    private String pWord;
    private String uName;

    public User(String name, String uName, String pWord) {
        this.name = name;
        this.uName = uName;
        this.pWord = pWord;
    }

    public User(String name) {
        this(name, null, null);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getPWord() {
        return pWord;
    }

    public void setPWord(String pWord) {
        this.pWord = pWord;
    }

    public String getUName() {
        return uName;
    }
}