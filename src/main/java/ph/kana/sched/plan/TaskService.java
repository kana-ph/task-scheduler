package ph.kana.sched.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

	private final TaskRepository taskRepository;

	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public List<Task> fetchAllByPlan(ProjectPlan plan) {
		Iterable<Task> result = taskRepository.findAllByPlan(plan);
		List<Task> tasks = new ArrayList<>();
		result.forEach(tasks::add);
		return tasks;
	}

	public Optional<Task> fetchByPlanAndId(ProjectPlan plan, Long taskId) {
		return taskRepository.findByPlanAndId(plan, taskId);
	}
}
