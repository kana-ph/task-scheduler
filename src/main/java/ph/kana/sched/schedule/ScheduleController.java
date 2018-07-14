package ph.kana.sched.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ph.kana.sched.common.error.InvalidPlanException;
import ph.kana.sched.common.error.MissingRequiredFieldException;
import ph.kana.sched.plan.ProjectPlan;
import ph.kana.sched.plan.ProjectPlanService;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

	private final ProjectPlanService projectPlanService;
	private final ScheduleService scheduleService;

	@Autowired
	public ScheduleController(ProjectPlanService projectPlanService, ScheduleService scheduleService) {
		this.projectPlanService = projectPlanService;
		this.scheduleService = scheduleService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody ScheduleResponseEntity create(@RequestBody ScheduleRequestEntity requestEntity)
			throws InvalidPlanException, MissingRequiredFieldException {
		if (Objects.isNull(requestEntity.getPlanId())) {
			throw new MissingRequiredFieldException("planId");
		}
		ProjectPlan plan = projectPlanService.fetchById(requestEntity.getPlanId())
				.orElseThrow(InvalidPlanException::new);

		if (Objects.isNull(requestEntity.getStartDate())) {
			throw new MissingRequiredFieldException("startDate");
		}

		Schedule schedule = scheduleService.create(plan, requestEntity.getStartDate());
		return ScheduleResponseEntity.from(schedule);
	}
}
