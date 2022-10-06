package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.System.out;

class CalcAverageAtMidnightTest {
    @Autowired
    Environment environment;

    @Value("${server.port}")
    int aPort;

    @Test
    void runScheduledTask() throws UnknownHostException {
        // Port
      //  out.println(environment.getProperty("server.port"));

        // Local address
        out.println(InetAddress.getLocalHost().getHostAddress());
        out.println(InetAddress.getLocalHost().getHostName());

        // Remote address
        out.println(InetAddress.getLoopbackAddress().getHostAddress());
        out.println(InetAddress.getLoopbackAddress().getHostName());
    }
}