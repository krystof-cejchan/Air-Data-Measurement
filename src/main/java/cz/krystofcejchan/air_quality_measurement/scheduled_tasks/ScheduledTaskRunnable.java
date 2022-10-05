package cz.krystofcejchan.air_quality_measurement.scheduled_tasks;

@FunctionalInterface
public interface ScheduledTaskRunnable {
    abstract public void runScheduledTask();
}
