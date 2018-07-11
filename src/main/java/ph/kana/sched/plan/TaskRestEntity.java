package ph.kana.sched.plan;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TaskRestEntity {

	private Long id;
	private String name;
	private int dayDuration;
	private List<Long> dependecyIds;

	public static TaskRestEntity from(Task task) {
		TaskRestEntity response = new TaskRestEntity();
		response.id = task.getId();
		response.name = task.getName();
		response.dayDuration = task.getDayDuration();
		response.dependecyIds = task.getDependencies()
				.stream()
				.map(Task::getId)
				.collect(Collectors.toList());
		return response;
	}
}
