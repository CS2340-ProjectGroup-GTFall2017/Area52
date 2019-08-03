package com.example.android.Rat_Tracking_Application.app.test;

import org.junit.Assert;
import org.junit.Test;

import area52.rat_tracking_application.model.RatReportLoader;

/**
 * Created by eunjikang on 11/13/17.
 */

public class ContainsTest {

    private final RatReportLoader c = new RatReportLoader();

    @Test
    public void isContain() throws Exception {
        String[] arr = {};
        String element = "New York";
        Assert.assertEquals(false, c.contains(arr, element));
    }

    @Test
    public void isContain1() throws Exception {
        String[] arr = null;
        String element = "New York";
        Assert.assertEquals(false, c.contains(arr, element));
    }

    @Test
    public void isContain2() throws Exception {
        String[] arr = {"New York", "Brooklyn"};
        String element = "New York";
        Assert.assertEquals(true, c.contains(arr, element));
    }

    @Test
    public void isContain3() throws Exception {
        String[] arr = {"New York", "Brooklyn"};
        String element = null;
        Assert.assertEquals(false, c.contains(arr, element));
    }

    @Test
    public void isContain4() throws Exception {
        String[] arr = {"New York", "Brooklyn", "Manhattan"};
        String element = "";
        Assert.assertEquals(false, c.contains(arr, element));
    }

    @Test
    public void isContain5() throws Exception {
        String[] arr = {123};
        String element = "New York";
        Assert.assertEquals(false, c.contains(arr, element));
    }

    @Test
    public void isContain6() throws Exception {
        String[] arr = {"New York"};
        String element = "Manhattan";
        Assert.assertEquals(false, c.contains(arr, element));
    }
}

