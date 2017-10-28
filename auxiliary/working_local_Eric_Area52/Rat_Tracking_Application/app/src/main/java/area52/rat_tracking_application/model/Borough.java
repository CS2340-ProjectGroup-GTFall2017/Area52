package area52.rat_tracking_application.model;

/**
 * Created by Eric on 10/26/2017.
 *
 * Acknowledgements: Template created by Prof. Robert Waters on 1/5/17
 * [GaTech - Fall 2017 - cs2340 - Objects & Design]
 */

public enum Borough {
    THE_BRONX ("The Bronx", "BRX"),
    QUEENS ("Queens", "QNS"),
    MANHATTAN ("Manhattan", "MTN"),
    STATEN_ISLAND ("Staten Island", "STI"),
    BROOKLYN ("Brooklyn", "BRK"),
    XX ("No Such Borough", "NULL");

    /** the full string representation of the borough name */
    private final String name;

    /** the representation of the borough name abbreviation*/
    private final String code;

    /**
     * Constructor for the enumeration
     *
     * @param pname   full name of the borough
     * @param pcode   letter code / abbreviation for the borough
     */
    Borough(String pname, String pcode) {
        name = pname;
        code = pcode;
    }

    /**
     *
     * @return   the full borough name
     */
    public String getName() { return name; }


    /**
     *
     * @return the abbreviation for the borough
     */
    public String getCode() { return code; }

    /**
     *
     * @return the display string representation of the borough
     */
    public String toString() { return code; }
}
