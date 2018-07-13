package ph.kana.sched.plan;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
@Setter
public class TaskRestEntity {

	private Long id;
	private String name;
	private Integer dayDuration;
	private List<Long> dependencyIds;

	public static TaskRestEntity from(Task task) {
		TaskRestEntity response = new TaskRestEntity();
		response.id = task.getId();
		response.name = task.getName();
		response.dayDuration = task.getDayDuration();
		List<Task> taskDependencies = task.getDependencies();

		if (Objects.nonNull(taskDependencies) && !taskDependencies.isEmpty()) {
			response.dependencyIds = task.getDependencies()
					.stream()
					.map(Task::getId)
					.collect(Collectors.toList());
		} else {
			response.dependencyIds = Collections.emptyList();
		}

		return response;
	}

	public Task toTask(ProjectPlan plan, List<Task> taskDependencies) {
		Task task = new Task();
		task.setName(name);
		task.setDayDuration(dayDuration);
		task.setPlan(plan);

		if (Objects.nonNull(taskDependencies) && !taskDependencies.isEmpty()) {
			task.setDependencies(taskDependencies);
		}

		return task;
	}

	private static void whenTaskDependenciesExists(List<Task> taskDependencies, Consumer<List<Task>> action) {
		if (Objects.nonNull(taskDependencies) && !taskDependencies.isEmpty()) {
			action.accept(taskDependencies);
		}
	}
}
