package cz.krystofcejchan.air_quality_measurement.scheduled_tasks;

import cz.krystofcejchan.air_quality_measurement.repository.AirDataAverageOfDayRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.UpdateForecast;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.calc_avg.CalcAverageAtMidnight;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Scheduled task runnable manager.
 */
public class ScheduledTaskRunnableManager {
    private final List<ScheduledTaskRunnable> runnables = new ArrayList<>();


    /**
     * Instantiates a new Scheduled task runnable manager.
     */
    public ScheduledTaskRunnableManager(AirDataAverageOfDayRepository avgRepository, AirDataRepository airDataRepository,
                                        LocationDataRepository locationDataRepository) {
        this();
        addToList(new CalcAverageAtMidnight(avgRepository, airDataRepository, locationDataRepository));
    }

    public ScheduledTaskRunnableManager() {
        addToList(new UpdateForecast());
    }

    private void addToList(ScheduledTaskRunnable taskRunnable) {
        if (runnables.stream().noneMatch(task -> task.equals(taskRunnable)))
            runnables.add(taskRunnable);
    }

    /**
     * Gets runnable list.
     *
     * @return the runnable list
     */
    public List<ScheduledTaskRunnable> getRunnableList() {
        return runnables;
    }
}
