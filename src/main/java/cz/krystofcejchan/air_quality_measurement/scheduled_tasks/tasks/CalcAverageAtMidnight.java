package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks;

import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The type Calc average at midnight.
 */
@RestController

public class CalcAverageAtMidnight implements ScheduledTaskRunnable {
    /**
     * The Environment.
     */
    @Autowired
    Environment environment;

    @Override
    public void runScheduledTask() {
        //new ScheduledExecutorService with one assigned thread
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        //what happens when service is triggered
        Runnable calcAverageDataAndAddToDatabase = () -> {
            //add to avg database
            String protocol = "http://";
            String localhost = InetAddress.getLoopbackAddress().getHostAddress();
            String path = "/airdata/avg/calc";
            System.out.println(protocol + localhost + ":8080" + path);
            try {
                URL url = new URL(protocol + localhost + ":8080" + path);
                HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
                httpUrlConn.setRequestMethod("GET");
                httpUrlConn.connect();
                System.out.println(httpUrlConn.getResponseCode() < 299 ? "average calculated" : "average NOT calculated");
                httpUrlConn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        //first trigger at 00:00, then wait one day to trigger again
        service.scheduleAtFixedRate(calcAverageDataAndAddToDatabase,
                LocalDateTime.now()
                        .until(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIN),
                                //  LocalDateTime.of(LocalDate.now(), LocalTime.now().plusSeconds(15)),
                                ChronoUnit.MINUTES),
                TimeUnit.DAYS.toMinutes(1),
                TimeUnit.MINUTES);
    }
}
