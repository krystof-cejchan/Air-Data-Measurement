package cz.krystofcejchan.air_quality_measurement.service;

import org.junit.jupiter.api.Test;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberDiffTest {

    @Test
    void calcDiff() {
        double a = 130;
        double b = -100;
        //diff is supposed to be 230
        assertEquals(230, algo(a, b));

    }

    private double algo(double a, double b) {
        if(a==b) return 0;
        if(a<0&&b<0)
        {
            return abs(abs(a)-abs(b));
        }
        else
            return abs(a-b);

    }

}