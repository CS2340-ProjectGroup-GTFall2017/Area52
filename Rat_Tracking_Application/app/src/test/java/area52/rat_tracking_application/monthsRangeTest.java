package area52.rat_tracking_application;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test file to test the mothsRange() method in GraphActicity
 *  - Created by Heejoo Cho (hcho328) for M10
 */

public class monthsRangeTest {
    private GraphActivity loader;
    private Random rand;

    @Before
    public void setUpIsNumTests() {
        loader = new GraphActivity();
        rand = new Random();
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyStartDate() {
        loader.monthsRange(null, new Date(2000, 0, 0));
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyEndDate() {
        loader.monthsRange(new Date(2000, 0, 0), null);
    }

    @Test
    public void testSameDate() {
        Date testDate = new Date(2000, 0, 0);
        assertEquals(1, loader.monthsRange(testDate, testDate).size());
    }

    @Test
    public void testSameMonthAnyDay() {
        Date testDate = new Date(2000, 0, 1);
        Date testDate2 = new Date(2000, 0, rand.nextInt(1) + 30);
        assertEquals(1, loader.monthsRange(testDate, testDate2).size());
    }

    @Test
    public void testNumberOfMonths() {
        Date testStart = new Date(2000, 0, 0);
        Date testEnd = new Date(2001, 0, 0);
        assertEquals(13, loader.monthsRange(testStart, testEnd).size());
    }

    @Test
    public void testMonths() {
        Date[] actualMonths = {
                new Date(2000, 0, 0),
                new Date(2000, 1, 0),
                new Date(2000, 2, 0),
                new Date(2000, 3, 0),
                new Date(2000, 4, 0),
                new Date(2000, 5, 0),
                new Date(2000, 6, 0)
        };

        Date testStart = new Date(2000, 0, 0);
        Date testEnd = new Date(2000, 6, 0);
        Map<Date, Integer> testMonths = loader.monthsRange(testStart, testEnd);

        for (int i = 0; i < actualMonths.length; i++) {
            testMonths.containsKey(actualMonths[i]);
        }
        for (Integer j : testMonths.values()) {
            assertEquals((Integer) 0, j);
        }
    }
}
// private Map<Date, Integer> monthsRange(Date start, Date end) {
//         //making a list(map) for all range of months chosen
//         Map<Date, Integer> monthRange = new HashMap<Date, Integer>();

//         int startYear = start.getYear(); //2016
//         int endYear = end.getYear(); // 2017
//         int startMonth = start.getMonth(); // 10
//         int endMonth = end.getMonth(); // 11

//         int x = startMonth; // 10
//         for (int y = startYear; y < endYear + 1; y++) { // y = 2017, while y < 2018, y++
//             if (y == endYear) {
//                 while (x < endMonth + 1) { // x = 0, x < 11 + 1
//                     monthRange.put(new Date(endYear, x, 0), 0); // 2017, 0, 0; 2017, 1, 0; 2017, 2, 0; ...
//                     x++;
//                 }
//             } else {
//                 while (x < GRAPH_MAX_X) {
//                     monthRange.put(new Date(y, x, 0), 0); // 2016, 10, 0; 2016, 11, 0;
//                     x++;
//                 }
//             }
//             x = 0;
//         }
//         return monthRange;
//     }