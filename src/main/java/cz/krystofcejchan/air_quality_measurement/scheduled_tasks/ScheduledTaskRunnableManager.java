package cz.krystofcejchan.air_quality_measurement.scheduled_tasks;

import cz.krystofcejchan.air_quality_measurement.notifications.NotificationsRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataAverageOfDayRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.UpdateForecast;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.calc_avg.CalcAverageAtMidnight;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.notifications.SendForecastForTheDay;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Scheduled task runnable manager.
 */
public class ScheduledTaskRunnableManager {
    private final List<ScheduledTaskRunnable> runnableList = new ArrayList<>();


    /**
     * Instantiates a new Scheduled task runnable manager.
     */
    public ScheduledTaskRunnableManager(AirDataAverageOfDayRepository avgRepository, AirDataRepository airDataRepository,
                                        LocationDataRepository locationDataRepository, NotificationsRepository notificationsRepository,
                                        JavaMailSender javaMailSender) {
        this();
        addToList(new CalcAverageAtMidnight(avgRepository, airDataRepository, locationDataRepository));
    }

    public ScheduledTaskRunnableManager() {
        addToList(new UpdateForecast());
    }

    private void addToList(ScheduledTaskRunnable taskRunnable) {
        if (runnableList.stream().noneMatch(task -> task.equals(taskRunnable)))
            runnableList.add(taskRunnable);

    }

    /**
     * Gets runnable list.
     *
     * @return the runnable list
     */
    public List<ScheduledTaskRunnable> getRunnableList() {
        return runnableList;
    }
}
