package ph.kana.sched.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ph.kana.sched.common.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/plan")
public class ProjectPlanController {

	private final ProjectPlanService projectPlanService;

	@Autowired
	public ProjectPlanController(ProjectPlanService projectPlanService) {
		this.projectPlanService = projectPlanService;
	}

	@GetMapping
	public @ResponseBody List<ProjectPlan> showAll() {
		return projectPlanService.fetchAll();
	}

	@GetMapping(value = "/{planId}")
	public @ResponseBody ProjectPlan show(@PathVariable Long planId) throws ResourceNotFoundException {
		return projectPlanService.fetchById(planId)
				.orElseThrow(ResourceNotFoundException::new);
	}
}
