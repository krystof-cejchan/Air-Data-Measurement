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
        long startTime = System.currentTimeMillis();

        SpringApplication.run(AqmApplication.class, args);

        Stream.iterate("*", i -> i + "*").limit(15).forEach(System.out::print);
        System.out.println("\nFully ready after: " + (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime)) + 's');
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
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

        scheduledExecutorService.schedule(recalculateLeaderboard, 10, TimeUnit.SECONDS);

        new InsertLocationData().runScheduledTask(locationDataRepository);

        new ScheduledTaskRunnableManager(avgRepository,
                airDataRepository,
                locationDataRepository,
                notificationsRepository,
                javaMailSender)
                .getRunnableList().forEach(ScheduledTaskRunnable::runScheduledTask);
    }


//    /**
//     * Cors filter
//     *
//     * @return the cors filter
//     */
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setAllowedOrigins(List.of("https://krystofcejchan.cz/"/*, "http://localhost:4200", "http://uwu.clanweb.eu/","http://localhost:4200", "31.30.115.190"*/));
//        corsConfiguration.setAllowedHeaders(Arrays.asList("*", "Origin", "Access-Control-Allow-Origin", "Content-Type",
//                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
//                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
//        corsConfiguration.setExposedHeaders(Arrays.asList("*","Origin", "Content-Type", "Accept", "Authorization",
//                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
//        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT"));
//        corsConfiguration.setMaxAge(Duration.of(1, ChronoUnit.MINUTES));
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//    }
    /*@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080");
			}
		};
	}*/
}

