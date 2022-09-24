package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.exceptions.DataNotFoundException;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class AirDataService {
    private final AirDataRepository airDataRepository;

    @Autowired
    @Contract(pure = true)
    public AirDataService(AirDataRepository airDataRepository) {
        this.airDataRepository = airDataRepository;
    }

    public AirData addAirData(@NotNull AirData airData) {
        airData.setRndHash(UUID.randomUUID().toString());
        airData.setReceivedDataDateTime(LocalDateTime.now(ZoneId.of("Europe/Prague")));
        return airDataRepository.save(airData);
    }

    public List<AirData> findAllAirData() {
        return airDataRepository.findAll();
    }

    public AirData updateAirData(AirData airData) {
        return airDataRepository.save(airData);
    }

    /**
     * @param id of the data in the database
     * @return AirData {@link AirData} object
     * @throws DataNotFoundException if no record with such an id exists
     */
    public AirData findAirData(Long id) throws DataNotFoundException {
        return airDataRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
    }

    public void deleteAirData(Long id) {
        airDataRepository.delete(findAirData(id));
    }

    public boolean existAirData(Long id) {
        return airDataRepository.existsById(id);
    }

}

