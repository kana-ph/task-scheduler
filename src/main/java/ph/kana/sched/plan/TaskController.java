package ph.kana.sched.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ph.kana.sched.common.error.ResourceNotFoundException;

import java.util.List;
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
		ProjectPlan plan = projectPlanService.fetchById(planId)
				.orElseThrow(ResourceNotFoundException::new);

		return taskService.fetchAllByPlan(plan)
				.stream()
				.map(TaskRestEntity::from)
				.collect(Collectors.toList());
	}

	@GetMapping("/{taskId}")
	public @ResponseBody TaskRestEntity show(@PathVariable Long planId, @PathVariable Long taskId) throws ResourceNotFoundException {
		ProjectPlan plan = projectPlanService.fetchById(planId)
				.orElseThrow(ResourceNotFoundException::new);

		return taskService.fetchByPlanAndId(plan, taskId)
				.map(TaskRestEntity::from)
				.orElseThrow(ResourceNotFoundException::new);
	}
}
