package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.exceptions.AlreadyInvalidData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

class AirDataServiceTest {

    @Test
    void compareAirDataObjects() throws AlreadyInvalidData {
       /* AirData airData1 = new AirData(null, null, null,
                BigDecimal.TEN, null, BigDecimal.TEN, BigDecimal.TEN, null,
                null, false), airData2 = new AirData(null, null, null,
                BigDecimal.valueOf(15), null, BigDecimal.TEN, BigDecimal.TEN, null,
                null, false);
        if (airData1.getInvalidData() || airData2.getInvalidData()) throw new AlreadyInvalidData();
        ArrayList<Boolean> airQ_temp_hum = new ArrayList<>();
        airQ_temp_hum.add(Math.abs(airData1.getAirQuality().doubleValue() - airData2.getAirQuality().doubleValue()) < 20);
        airQ_temp_hum.add(Math.abs(airData1.getTemperature().doubleValue() - airData2.getTemperature().doubleValue()) < 10);
        airQ_temp_hum.add(Math.abs(airData1.getHumidity().doubleValue() - airData2.getHumidity().doubleValue()) < 20);

        Assertions.assertTrue(airQ_temp_hum.stream().allMatch(Boolean::booleanValue));*/
    }

    @Test
    void updateNumberReportedById() {
        var a =LocalDateTime.of(2023,1,15,20,0);
        var b =LocalDateTime.of(2023,1,15,18,0);
        Assertions.assertTrue(b.until(a, ChronoUnit.MINUTES)>=2*60);
    }
}