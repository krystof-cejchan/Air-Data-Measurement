package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks;

import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import cz.krystofcejchan.air_quality_measurement.domain.location.StaticLocationData;
import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;

import java.util.ArrayList;
import java.util.List;

public class InsertLocationData {

    public void runScheduledTask(LocationDataRepository locationDataRepository) {
        //  final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable insertNewDataToLocationTable = () -> {
            List<LocationData> locationDataList = locationDataRepository.findAll();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //locationDataList.forEach(it -> System.out.println(it.getName()));
            List<LocationData> locationDataListToBeInserted = new ArrayList<>();
            List<String> locationDataName = locationDataList.stream().map(LocationData::getName).toList();
            StaticLocationData.ALL_LOCATIONS_Pre.forEach(locationData -> {
                if (!locationDataName.contains(locationData.getName())) {
                    locationDataListToBeInserted.add(locationData);
                }
            });
            locationDataRepository.saveAll(locationDataListToBeInserted);
            // System.out.println(locationDataListToBeInserted.size() + "\slocations were inserted to the db.");
        };
        insertNewDataToLocationTable.run();

    }
}
