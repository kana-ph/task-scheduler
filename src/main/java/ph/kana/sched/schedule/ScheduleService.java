package ph.kana.sched.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.kana.sched.plan.ProjectPlan;
import ph.kana.sched.task.Task;
import ph.kana.sched.task.TaskService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleService {

	private final TaskService taskService;

	@Autowired
	public ScheduleService(TaskService taskService) {
		this.taskService = taskService;
	}

	public Schedule create(ProjectPlan plan, LocalDate startDate) {
		Schedule schedule = new Schedule();
		schedule.setPlan(plan);
		schedule.setStartDate(startDate);

		List<Task> tasks = taskService.fetchAllByPlan(plan);
		tasks.forEach(task -> scheduleTask(schedule, task));

		return schedule;
	}

	private void scheduleTask(Schedule schedule, Task task) {
		List<Task> dependencies = task.getDependencies();
		ScheduledTask scheduledTask;

		if (dependencies.isEmpty()) {
			scheduledTask = createScheduledTask(task, schedule.getStartDate());
		} else {
			Map<Task, ScheduledTask> scheduledTasks = schedule.getTasks();
			LocalDate startDate = dependencies.stream()
					.filter(scheduledTasks::containsKey)
					.map(scheduledTasks::get)
					.map(ScheduledTask::getEndDate)
					.max(Comparator.naturalOrder())
					.orElseGet(schedule::getStartDate);
			scheduledTask = createScheduledTask(task, startDate);
		}

		schedule.getTasks()
				.put(task, scheduledTask);
	}

	private ScheduledTask createScheduledTask(Task task, LocalDate startDate) {
		ScheduledTask scheduledTask = new ScheduledTask();
		scheduledTask.setTask(task);
		scheduledTask.setStartDate(startDate);

		LocalDate endDate = startDate.plusDays(task.getDayDuration());
		scheduledTask.setEndDate(endDate);

		return scheduledTask;
	}
}
