package cz.krystofcejchan.air_quality_measurement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class equalsObjectsLong_Test {
    @Test
    void isLongObjectEqual() {
        Long l = 30L;
        Long l2 = 30L;

        Assertions.assertEquals(l.longValue(), l2.longValue());
    }


}