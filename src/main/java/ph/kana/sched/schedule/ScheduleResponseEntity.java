package ph.kana.sched.schedule;

import lombok.Getter;
import ph.kana.sched.plan.ProjectPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class ScheduleResponseEntity {

	private ProjectPlan plan;
	private LocalDate startDate;
	private List<ScheduledTask> tasks;

	public static ScheduleResponseEntity from(Schedule schedule) {
		ScheduleResponseEntity response = new ScheduleResponseEntity();
		response.plan = schedule.getPlan();
		response.startDate = schedule.getStartDate();
		response.tasks = new ArrayList<>(schedule.getTasks().values());

		Comparator<ScheduledTask> scheduleSort = Comparator.comparing(ScheduledTask::getStartDate)
				.thenComparing(ScheduledTask::getEndDate)
				.thenComparing(scheduledTask -> scheduledTask.getTask().getId());
		response.tasks.sort(scheduleSort);

		return response;
	}
}
