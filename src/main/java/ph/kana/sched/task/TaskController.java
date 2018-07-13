package ph.kana.sched.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ph.kana.sched.common.error.InvalidTaskDependencyException;
import ph.kana.sched.common.error.MissingRequiredFieldException;
import ph.kana.sched.common.error.ResourceNotFoundException;
import ph.kana.sched.common.error.ServiceValidationException;
import ph.kana.sched.plan.ProjectPlan;
import ph.kana.sched.plan.ProjectPlanService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/plan/{planId}/task")
public class TaskController {

	private final ProjectPlanService projectPlanService;
	private final TaskService taskService;

	@Autowired
	public TaskController(ProjectPlanService projectPlanService, TaskService taskService) {
		this.projectPlanService = projectPlanService;
		this.taskService = taskService;
	}

	@GetMapping
	public @ResponseBody List<TaskRestEntity> showAll(@PathVariable Long planId) throws ResourceNotFoundException {
		ProjectPlan plan = fetchProjectPlan(planId);

		return taskService.fetchAllByPlan(plan)
				.stream()
				.map(TaskRestEntity::from)
				.collect(Collectors.toList());
	}

	@GetMapping("/{taskId}")
	public @ResponseBody TaskRestEntity show(@PathVariable Long planId, @PathVariable Long taskId) throws ResourceNotFoundException {
		ProjectPlan plan = fetchProjectPlan(planId);

		return taskService.fetchByPlanAndId(plan, taskId)
				.map(TaskRestEntity::from)
				.orElseThrow(ResourceNotFoundException::new);
	}

	@PostMapping
	public @ResponseBody TaskRestEntity create(@PathVariable Long planId, @RequestBody TaskRestEntity taskEntity)
			throws ResourceNotFoundException, InvalidTaskDependencyException, MissingRequiredFieldException, ServiceValidationException {
		ProjectPlan plan = fetchProjectPlan(planId);
		List<Task> taskDependencies = fetchTaskDependencies(plan, taskEntity.getDependencyIds());

		validateTaskDependencies(taskDependencies, taskEntity);
		validateTask(taskEntity);

		Task task = taskEntity.toTask(plan, taskDependencies);
		task = taskService.create(task);

		return TaskRestEntity.from(task);
	}

	private ProjectPlan fetchProjectPlan(Long id) throws ResourceNotFoundException {
		return projectPlanService.fetchById(id)
				.orElseThrow(ResourceNotFoundException::new);
	}

	private void validateTask(TaskRestEntity task) throws MissingRequiredFieldException {
		String taskName = task.getName();
		if (Objects.isNull(taskName) || taskName.isEmpty()) {
			throw new MissingRequiredFieldException("name");
		}

		Integer duration = task.getDayDuration();
		if (Objects.isNull(duration)) {
			throw new MissingRequiredFieldException("dayDuration");
		}
	}

	private void validateTaskDependencies(List<Task> taskDependencies, TaskRestEntity taskEntity) throws InvalidTaskDependencyException {
		List<Long> dependencyIds = taskEntity.getDependencyIds();
		if ((Objects.nonNull(dependencyIds) && !dependencyIds.isEmpty()) && (taskDependencies.size() != dependencyIds.size())) {
			throw new InvalidTaskDependencyException();
		}
	}

	private List<Task> fetchTaskDependencies(ProjectPlan plan, List<Long> dependencyIds) {
		if (Objects.nonNull(dependencyIds) && !dependencyIds.isEmpty()) {
			return taskService.fetchAllByIds(plan, dependencyIds);
		} else {
			return Collections.emptyList();
		}
	}
}
