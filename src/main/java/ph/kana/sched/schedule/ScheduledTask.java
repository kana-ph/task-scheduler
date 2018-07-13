package ph.kana.sched.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ph.kana.sched.task.Task;

import java.time.LocalDate;

@Data
public class ScheduledTask {

	@JsonIgnoreProperties({"version", "plan", "dependencies"})
	private Task task;

	private LocalDate startDate;

	private LocalDate endDate;
}
