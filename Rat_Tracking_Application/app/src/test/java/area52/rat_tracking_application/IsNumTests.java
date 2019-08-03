package area52.rat_tracking_application;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import area52.rat_tracking_application.model.RatReportLoader;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test file to test the isNum() method in RatReportLoader
 *  - Created by Joseph Deerin (jdeerin3) for M10
 * Created by 2016d on 11/15/2017.
 */

public class IsNumTests {
    private RatReportLoader loader;
    private Random rand;

    @Before
    public void setUpIsNumTests() {
        loader = new RatReportLoader();
        rand = new Random();
    }

    private int randInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    @Test
    public void testEmptyString() {
        String testString = "";
        assertEquals(false, loader.isNum(testString));
    }

    @Test(expected = NullPointerException.class)
    public void testNullString() {
        String testString = null;
        loader.isNum(testString);
    }

    @Test
    public void testZero() {
        String testString = "0";
        assertEquals(true, loader.isNum(testString));
    }

    @Test
    public void testPositiveIntString() {
        String testString = "" + randInt(1, Integer.MAX_VALUE);
        assertEquals(true, loader.isNum(testString));
    }

    @Test
    public void testNegativeIntString() {
        //Overflow errors if I don't reduce MIN_VALUE
        String testString = "" + randInt(Integer.MIN_VALUE + 1, -1);
        assertEquals(true, loader.isNum(testString));
    }

    @Test
    public void testFloatString() {
        String testString = "" + rand.nextFloat();
        assertEquals(true, loader.isNum(testString));
    }

    @Test
    public void testDoubleString() {
        String testString = "" + rand.nextDouble();
        assertEquals(true, loader.isNum(testString));
    }

    @Test
    public void testLongString() {
        String testString = "" + rand.nextLong();
        assertEquals(true, loader.isNum(testString));
    }

    @Test
    public void testStringWithoutDigits() {
        assertEquals(false, loader.isNum("nope"));
    }

    @Test
    public void testStringWithDigits() {
        assertEquals(false, loader.isNum("c00l"));
    }

    @Test
    public void testDoubleWithMultipleDecimalPoints() {
        assertEquals(false, loader.isNum("1.000.1"));
    }

    @Test
    public void testHexadecimalString() {
        assertEquals(true, loader.isNum("0x1337"));
    }

    @Test
    public void testStringWithMathOperation() {
        assertEquals(true, loader.isNum("1+1"));
    }
}
