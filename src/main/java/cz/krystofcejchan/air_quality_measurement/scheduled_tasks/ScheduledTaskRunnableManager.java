package cz.krystofcejchan.air_quality_measurement.scheduled_tasks;

import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.CalcAverageAtMidnight;

import java.util.ArrayList;
import java.util.List;

public class ScheduledTaskRunnableManager {
    private final List<ScheduledTaskRunnable> runnables = new ArrayList<>();

    public ScheduledTaskRunnableManager() {
        addToList(new CalcAverageAtMidnight());
    }

    private void addToList(ScheduledTaskRunnable taskRunnable) {
        if (runnables.stream().noneMatch(task -> task.equals(taskRunnable)))
            runnables.add(taskRunnable);
    }

    public List<ScheduledTaskRunnable> getRunnableList() {
        return runnables;
    }
}
