package ph.kana.sched.plan;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

	List<Task> findAllByPlan(ProjectPlan plan);

	Optional<Task> findByPlanAndId(ProjectPlan plan, Long id);
}
