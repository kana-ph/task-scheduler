package ph.kana.sched.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.kana.sched.common.error.ApiError;
import ph.kana.sched.common.error.ServiceValidationException;
import ph.kana.sched.plan.ProjectPlan;

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
		Iterable<Task> tasks = taskRepository.findAllByPlan(plan);
		return transformIterableTasksToList(tasks);
	}

	public Optional<Task> fetchByPlanAndId(ProjectPlan plan, Long taskId) {
		return taskRepository.findByPlanAndId(plan, taskId);
	}

	public List<Task> fetchAllByIds(ProjectPlan plan, List<Long> ids) {
		Iterable<Task> tasks = taskRepository.findAllByPlanAndIdIn(plan, ids);
		return transformIterableTasksToList(tasks);
	}

	public Task create(Task task) throws ServiceValidationException {
		if (task.getDayDuration() < 0) {
			throw new ServiceValidationException(ApiError.DURATION_NOT_POSITIVE);
		}
		return taskRepository.save(task);
	}

	private List<Task> transformIterableTasksToList(Iterable<Task> tasks) {
		List<Task> taskList = new ArrayList<>();
		tasks.forEach(taskList::add);
		return taskList;
	}
}
