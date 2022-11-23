package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.exceptions.AlreadyInvalidData;
import cz.krystofcejchan.air_quality_measurement.exceptions.DataNotFoundException;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.utilities.MathUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * The type Air data service.
 */
@Service
public class AirDataService {
    @Autowired
    AirDataLeaderboardService leaderboardService;
    @Autowired
    AirDataRepository airDataRepository;

    /**
     * Instantiates a new Air data service.
     */
    @Autowired
    @Contract(pure = true)
    public AirDataService() {
    }

    /**
     * Add air data air data.
     * if airData are not valid(values are most likely to be incorrect), nothing will be saved
     *
     * @param airData the air data
     * @return the air data
     */
    public @NotNull AirData addAirData(@NotNull AirData airData) {
        if (!areDataValid(airData)) return airData;

        airData.setReceivedDataDateTime(LocalDateTime.now(ZoneId.of("Europe/Prague")));
        airData.setRndHash(UUID.randomUUID().toString());

        AirData airDataSave = airDataRepository.save(airData);
      /*  try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        leaderboardService.updateLeaderboard();
        return airDataSave;
    }

    /**
     * Find all air data list.
     *
     * @return the list
     */
    public @NotNull List<AirData> findAllAirData() {
        return airDataRepository.findAll();
    }

    /**
     * Update air data air data.
     *
     * @param airData the air data
     * @return the air data
     */
    public @NotNull AirData updateAirData(AirData airData) {
        return airDataRepository.save(airData);
    }

    /**
     * report a data {@link AirData} - adding +1 to reportedN column in database
     *
     * @param id identifier of the record
     * @return AirData or null if no record matched the id
     */
    @Nullable
    public AirData updateNumberReportedById(Long id) {
        if (airDataRepository.findById(id).isPresent()) {
            AirData airData = airDataRepository.findById(id).get();
            airData.setReportedN(airData.getReportedN() + 1);

          /*  AirData previousAirData = airDataRepository.findByLocationAndReceivedDataDateTimeBefore(airData.getLocation(),
                            airData.getReceivedDataDateTime())
                    .orElse(Collections.singletonList(airData))
                    .get(0);*/

            List<AirData> optionalPreviousAirData = airDataRepository.findByLocationAndReceivedDataDateTimeBefore(airData.getLocation(),
                            airData.getReceivedDataDateTime())
                    .orElse(Collections.emptyList());

            AirData previousAirData = optionalPreviousAirData.isEmpty() ? airData : optionalPreviousAirData.get(0);

            if (!areDataValid(airData) || !compareAirDataObjects(airData, previousAirData)) {
                airData.setInvalidData(true);
            }
            return airDataRepository.save(airData);
        }
        return null;
    }


    /**
     * Find air data air data.
     *
     * @param id of the data in the database
     * @return AirData {@link AirData} object
     * @throws DataNotFoundException if no record with such an id exists
     */
    public AirData findAirData(Long id) throws DataNotFoundException {
        return airDataRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
    }

    /**
     * Delete air data.
     *
     * @param id the id
     */
    public void deleteAirData(Long id) {
        airDataRepository.delete(findAirData(id));
    }

    /**
     * Exist air data boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean existAirData(Long id) {
        return airDataRepository.existsById(id);
    }

    /**
     * compares currentAirData and previouslyAirData based on their attributes(airQuality,Temperature,Humidity)
     *
     * @param currentAirData    {@link AirData} #1
     * @param previouslyAirData {@link AirData}#2
     * @return true if the data are acceptable, else false
     * @throws AlreadyInvalidData currentAirData or previouslyAirData have been marked as invalid data!
     */
    @Contract(pure = true)
    private boolean compareAirDataObjects(@NotNull AirData currentAirData, AirData previouslyAirData) throws AlreadyInvalidData {
        if (currentAirData.getInvalidData() || previouslyAirData.getInvalidData()) throw new AlreadyInvalidData();
        ArrayList<Boolean> airQ_temp_hum = new ArrayList<>();

        airQ_temp_hum.add(Math.abs(currentAirData.getAirQuality().doubleValue() - previouslyAirData.getAirQuality().doubleValue()) < 20);
        airQ_temp_hum.add(Math.abs(currentAirData.getTemperature().doubleValue() - previouslyAirData.getTemperature().doubleValue()) < 10);
        airQ_temp_hum.add(Math.abs(currentAirData.getHumidity().doubleValue() - previouslyAirData.getHumidity().doubleValue()) < 20);

        return airQ_temp_hum.stream().allMatch(Boolean::booleanValue);
    }

    @Contract(pure = true)
    private boolean areDataValid(@NotNull AirData airData1) throws AlreadyInvalidData {
        ArrayList<Boolean> airQ_temp_hum = new ArrayList<>();

        airQ_temp_hum.add(MathUtils.isInBetween(airData1.getAirQuality(), 0, 200, false));
        airQ_temp_hum.add(MathUtils.isInBetween(airData1.getTemperature(), -20, 45, true));
        airQ_temp_hum.add(MathUtils.isInBetween(airData1.getHumidity(), 0, 100, true));

        return airQ_temp_hum.stream().allMatch(Boolean::booleanValue);
    }

}

