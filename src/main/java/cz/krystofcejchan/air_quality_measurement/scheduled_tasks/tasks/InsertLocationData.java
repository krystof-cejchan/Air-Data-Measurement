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
          /*  try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            ArrayList<LocationData> locationDataListToBeInserted = new ArrayList<>();
            List<String> locationDataName = locationDataList.stream().map(LocationData::getName).toList();

            for (LocationData ld : StaticLocationData.ALL_LOCATIONS_Pre)
                if (!locationDataName.contains(ld.getName()))
                    locationDataListToBeInserted.add(ld);

            locationDataRepository.saveAll(locationDataListToBeInserted);
        };
        insertNewDataToLocationTable.run();
    }
}
