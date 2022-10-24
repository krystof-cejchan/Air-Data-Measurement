package cz.krystofcejchan.air_quality_measurement.scheduled_tasks;

import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.CalcAverageAtMidnight;

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
    public ScheduledTaskRunnableManager() {
        addToList(new CalcAverageAtMidnight());
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
