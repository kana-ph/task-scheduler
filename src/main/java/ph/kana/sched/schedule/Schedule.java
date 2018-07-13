package ph.kana.sched.schedule;

import lombok.Data;
import ph.kana.sched.plan.ProjectPlan;
import ph.kana.sched.task.Task;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Schedule {

	public  Schedule() {
		this.tasks = new LinkedHashMap<>();
	}

	private ProjectPlan plan;
	private LocalDate startDate;
	private Map<Task, ScheduledTask> tasks;
}
