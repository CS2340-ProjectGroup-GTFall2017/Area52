package area52.rat_tracking_application.model;

import java.lang.StringBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Eric on 9/24/2017.
 */

public class User<T> {
    private T uName;
    private T email;
    private T pWord;
    private String userInfo;
    private static StringBuffer strBuffer = new StringBuffer();

    public User(T uName, T email, T pWord) {
        this.uName = uName;
        this.email = email;
        this.pWord = pWord;
    }

    public User() {
        this(null, null, null);
    }

    public void setUserInfo(){
        userInfo = uName.toString() + email.toString() + pWord.toString();
        setBuffer(userInfo);
    }

    public String getUserInfo() {
        return userInfo;
    }

    public static void setBuffer(String userInfo) {
        strBuffer.append(userInfo);
    }

    public static String getBufferAsString() {
        return strBuffer.toString();
    }

    public void clear() {
        strBuffer.replace(0, strBuffer.length(), "");
    }

    public void setUName(T uName) {
        this.uName = uName;
    }

    public T getUName() {
        return uName;
    }

    public void setEmail(T email) {
        this.email = email;
    }

    public T getEmail() {
        return email;
    }

    public void setPWord(T pWord) {
        this.pWord = pWord;
    }

    public T getPWord() {
        return pWord;
    }
}