package cz.krystofcejchan.air_quality_measurement.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AirDataService2Test {

    @Test
    void reportAirDataById() {
        final String P = "password132";
        String pswd = null;
        boolean isAuthRequest = !(pswd == null || pswd.isEmpty() || pswd.isBlank() || !pswd.equals(P));
        Assertions.assertTrue(isAuthRequest);
    }
}