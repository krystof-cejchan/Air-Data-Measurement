package cz.krystofcejchan.air_quality_measurement;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.notifications.NotificationsRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataAverageOfDayRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataLeaderboardRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnable;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnableManager;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.InsertLocationData;
import cz.krystofcejchan.air_quality_measurement.utilities.leaderboard.table.LeaderBoardKey;
import cz.krystofcejchan.air_quality_measurement.utilities.leaderboard.table.LeaderboardTable;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Main Class
 */
@SpringBootApplication
@EnableScheduling
public class AqmApplication implements CommandLineRunner {

    @Autowired
    private AirDataLeaderboardRepository airDataLeaderboardRepo;
    @Autowired
    private AirDataRepository airDataRepo;
    @Autowired
    private LocationDataRepository locationDataRepository;
    @Autowired
    private AirDataAverageOfDayRepository avgRepository;
    @Autowired
    private AirDataRepository airDataRepository;
    @Autowired
    private NotificationsRepository notificationsRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String @NotNull ... args) {
        final long startTime = System.currentTimeMillis();

        SpringApplication.run(AqmApplication.class, args);

        Stream.iterate("*", s -> s + '*').parallel().limit(15).forEach(System.out::print);
        System.out.println("\nFully ready after: " + (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime)) + 's');
    }

    /**
     * implemented from {@link CommandLineRunner}
     *
     * @param args common String... args
     * @throws Exception implemented from {@link CommandLineRunner}
     */
    @Override
    public void run(String... args) throws Exception {

        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Runnable recalculateLeaderboard = () -> {
            List<AirDataLeaderboard> existingAirData = airDataLeaderboardRepo.findAll();
            Map<LeaderBoardKey, List<AirData>> map = LeaderboardTable.getFreshDataForLeaderboard(airDataRepo);
            LeaderboardTable.saveChangedDataAndDeleteOldData(airDataLeaderboardRepo, existingAirData, map);
        };
        scheduledExecutorService.schedule(recalculateLeaderboard, 100, TimeUnit.SECONDS);

        new InsertLocationData().runScheduledTask(locationDataRepository);

        new ScheduledTaskRunnableManager(avgRepository,
                airDataRepository,
                locationDataRepository,
                notificationsRepository,
                javaMailSender)
                .getRunnableList().forEach(ScheduledTaskRunnable::runScheduledTask);
    }
}

