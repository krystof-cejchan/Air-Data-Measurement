package cz.krystofcejchan.air_quality_measurement.utilities.psw;

import cz.krystofcejchan.air_quality_measurement.enums.Production;

public class Psw {
    public final static char[] dbpsd = System.getenv("DBPSD").toCharArray();
    public final static char[] ardtkn = System.getenv("ARDTKN").toCharArray();
    public final static Production production = Production.valueOf(System.getenv("PROD"));
}
