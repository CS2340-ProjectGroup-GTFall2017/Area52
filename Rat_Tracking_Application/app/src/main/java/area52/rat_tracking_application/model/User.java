package area52.rat_tracking_application.model;

/**
 * Created by Eric on 9/24/2017.
 */

public class User {
    private String name;
    private String pWord;
    private String uName;
    public User(String name, String pWord, String uName) {
        this.name = name;
        this.pWord = pWord;
        this.uName = uName;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setPWord(char[] pWordAsChars) {
        this.pWord = pWordAsChars.toString();
    }
    public String getPWord() {
        return pWord;
    }
    public void setUName(char[] uNameAsChars) {
        this.uName = uNameAsChars.toString();
    }
    public String getUName() {
        return uName;
    }
}