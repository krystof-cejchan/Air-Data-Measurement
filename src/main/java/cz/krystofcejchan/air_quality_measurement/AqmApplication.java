package cz.krystofcejchan.air_quality_measurement;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataLeaderboardRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnable;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnableManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.*;
import java.util.stream.Stream;

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

        List<AirData> existingAirData = airDataLeaderboardRepo.findAll().stream().map(AirDataLeaderboard::getAirDataId).toList();
        sleep(2500);
        Map<LeaderBoardKey, List<AirData>> map = this.getFreshDataForLeaderboard();
        sleep(5000);

        saveChangedData(existingAirData, map);

    }

    private void saveChangedData(List<AirData> existingAirData, @NotNull Map<LeaderBoardKey, List<AirData>> map) {
        map.forEach((key, value) -> value.forEach(airData -> {
            if (existingAirData.stream().noneMatch(data -> Objects.equals(data.getId(), airData.getId()))) {
                airDataLeaderboardRepo.save(new AirDataLeaderboard(airData,
                        key.getT(), airData.getLocation(), value.indexOf(airData) + 1));
            }
        }));
    }

    /**
     * Prepares data from AirData database to be inserted into the leaderboard database
     *
     * @return Map {@link LeaderBoardKey} to {@link List} of {@link AirData}
     */
    @Contract(pure = true)
    public @NotNull Map<LeaderBoardKey, List<AirData>> getFreshDataForLeaderboard() {
        Map<LeaderBoardKey, List<AirData>> map = new HashMap<>();


        for (LeaderboardType leaderboardType : LeaderboardType.values()) {
            switch (leaderboardType) {
                case HIGHEST_HUM -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3HumidityByOrderByHumidityDesc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
                case LOWEST_HUM -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3HumidityByOrderByHumidityAsc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
                case HIGHEST_AIRQ -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3AirQualityByOrderByAirQualityDesc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
                case LOWEST_AIRQ -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3AirQualityByOrderByAirQualityAsc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
                case HIGHEST_TEMP -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3TemperatureByOrderByTemperatureDesc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
                case LOWEST_TEMP -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3TemperatureDistinctByOrderByTemperatureAsc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
            }

        }

        map.values().removeIf(values -> values.isEmpty() || values.stream().anyMatch(value -> value.getId() < 1));
        Stream.iterate('*', i -> i)
                .limit(2000)
                .forEach(System.out::print);
        map.forEach((k, v) -> System.out.println(k + " → " + v));
        return map;
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

record LeaderBoardKey(LeaderboardType t) {
    @Contract(pure = true)
    LeaderBoardKey {
    }

    @Contract(pure = true)
    public LeaderboardType getT() {
        return t;
    }
}
