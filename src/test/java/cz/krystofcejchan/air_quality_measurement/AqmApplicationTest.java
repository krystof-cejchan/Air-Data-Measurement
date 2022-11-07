package cz.krystofcejchan.air_quality_measurement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class AqmApplicationTest {

    @Test
    @Autowired
    void getFreshDataForLeaderboard() {
        System.out.println(new AqmApplication().getFreshDataForLeaderboard());
    }
}