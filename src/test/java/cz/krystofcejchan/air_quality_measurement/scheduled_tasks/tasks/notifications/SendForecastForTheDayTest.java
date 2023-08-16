package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.notifications;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class SendForecastForTheDayTest {

    @Test
    void getHttpStatusToString() {
        HttpStatus httpStatus = HttpStatus.OK;
        System.out.println(httpStatus.isError());
        Assertions.assertFalse(httpStatus.isError());
    }

}