package cz.krystofcejchan.air_quality_measurement.api.resource;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class AirDataApiResourceTest {

    @Test
    void getAirDataForSpecificDay() {
        System.out.println(LocalDate.now());
        LocalDate.parse("2022-09-01");//YYYY-MM-DD
    }
}