package ph.kana.sched.task;

import org.springframework.data.repository.CrudRepository;
import ph.kana.sched.plan.ProjectPlan;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

	List<Task> findAllByPlan(ProjectPlan plan);

	List<Task> findAllByPlanAndIdIn(ProjectPlan plan, List<Long> ids);

	Optional<Task> findByPlanAndId(ProjectPlan plan, Long id);
}