package ph.kana.sched.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ph.kana.sched.common.error.MissingRequiredFieldException;
import ph.kana.sched.common.error.ResourceNotFoundException;
import ph.kana.sched.common.error.ServiceValidationException;

import java.util.List;
import java.util.Objects;

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

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody ProjectPlan create(@RequestBody ProjectPlan plan) throws MissingRequiredFieldException, ServiceValidationException {
		String planName = plan.getName();
		if (Objects.isNull(planName) || planName.isEmpty()) {
			throw new MissingRequiredFieldException("name");
		}

		return projectPlanService.create(plan);
	}
}
