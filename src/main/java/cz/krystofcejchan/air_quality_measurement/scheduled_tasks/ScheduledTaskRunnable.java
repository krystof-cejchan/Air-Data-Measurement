package cz.krystofcejchan.air_quality_measurement.scheduled_tasks;

/**
 * The interface Scheduled task runnable.
 */
@FunctionalInterface
public interface ScheduledTaskRunnable {
    /**
     * Run scheduled task.
     */
    abstract public void runScheduledTask();
}
