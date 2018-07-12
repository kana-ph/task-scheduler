package ph.kana.sched.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.kana.sched.common.error.ApiError;
import ph.kana.sched.common.error.ServiceValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectPlanService {

	private final ProjectPlanRepository projectPlanRepository;

	@Autowired
	public ProjectPlanService(ProjectPlanRepository projectPlanRepository) {
		this.projectPlanRepository = projectPlanRepository;
	}

	public List<ProjectPlan> fetchAll() {
		Iterable<ProjectPlan> result = projectPlanRepository.findAll();
		List<ProjectPlan> projectPlans = new ArrayList<>();
		result.forEach(projectPlans::add);

		return projectPlans;
	}

	public Optional<ProjectPlan> fetchById(Long id) {
		return projectPlanRepository.findById(id);
	}

	public ProjectPlan create(ProjectPlan plan) throws ServiceValidationException {
		if (projectPlanRepository.existsByName(plan.getName())) {
			throw new ServiceValidationException(ApiError.PLAN_NAME_EXISTS);
		}
		return projectPlanRepository.save(plan);
	}
}
