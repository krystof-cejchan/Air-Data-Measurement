package cz.krystofcejchan.air_quality_measurement;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataLeaderboardRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnable;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnableManager;
import cz.krystofcejchan.air_quality_measurement.utilities.leaderboard.table.LeaderBoardKey;
import cz.krystofcejchan.air_quality_measurement.utilities.leaderboard.table.LeaderboardTable;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Main Class
 */
@SpringBootApplication
public class AqmApplication implements CommandLineRunner {
    /**
     * a secret passed to the database
     */
    public static String dbpsd = "";

    @Autowired
    private AirDataLeaderboardRepository airDataLeaderboardRepo;
    @Autowired
    private AirDataRepository airDataRepo;

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String @NotNull [] args) {
        dbpsd = args[0];
        SpringApplication.run(AqmApplication.class, args);
        new ScheduledTaskRunnableManager().getRunnableList().forEach(ScheduledTaskRunnable::runScheduledTask);
    }


    /**
     * implemented from {@link CommandLineRunner}
     *
     * @param args common String... args
     * @throws Exception implemented from {@link CommandLineRunner}
     */
    @Override
    public void run(String... args) throws Exception {

        List<AirDataLeaderboard> existingAirData = airDataLeaderboardRepo.findAll();
        sleep(2500);
        Map<LeaderBoardKey, List<AirData>> map = LeaderboardTable.getFreshDataForLeaderboard(airDataRepo);
        sleep(5000);

        LeaderboardTable.saveChangedDataAndDeleteOldData(airDataLeaderboardRepo, existingAirData, map);

    }


    /**
     * Cors filter
     *
     * @return the cors filter
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}

