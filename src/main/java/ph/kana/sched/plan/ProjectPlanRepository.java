package ph.kana.sched.plan;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPlanRepository extends CrudRepository<ProjectPlan, Long> {

	boolean existsByName(String name);
}
