package ph.kana.sched.schedule

import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import ph.kana.sched.plan.ProjectPlan
import ph.kana.sched.task.Task
import ph.kana.sched.task.TaskService
import java.time.LocalDate
import java.util.*

@SpringBootTest
class ScheduleServiceTest {

	private val taskService: TaskService = mock(TaskService::class.java)

	private lateinit var service: ScheduleService

	@Before
	fun setup() {
		service = ScheduleService(taskService)
	}

	@Test
	fun `create should create a schedule from a plan when inputs are valid`() {
		val plan = ProjectPlan()
		val startDate = LocalDate.now()
		val tasks = Arrays.asList(mock(Task::class.java))

		given(taskService.fetchAllByPlan(plan))
				.willReturn(tasks)

		val result = service.create(plan, startDate)

		assertSame(plan, result.plan)
		assertEquals(startDate, result.startDate)
	}

	@Test
	fun `create should create a scheduled task based on the given start date when a task has no dependency`() {
		val plan = ProjectPlan()
		val taskDuration = 2
		val startDate = LocalDate.now()

		val task = mock(Task::class.java)
		given(task.dependencies)
				.willReturn(Collections.emptyList())
		given(task.dayDuration)
				.willReturn(taskDuration)
		val endDate = startDate.plusDays(taskDuration.toLong())

		given(taskService.fetchAllByPlan(plan))
				.willReturn(Arrays.asList(task))

		val result = service.create(plan, startDate)

		val scheduledTask = result.tasks[task]!!
		assertEquals(startDate, scheduledTask.startDate)
		assertEquals(endDate, scheduledTask.endDate)
	}

	@Test
	fun `create should create a scheduled task based on a dependency when a task has a dependency`() {
		val plan = ProjectPlan()
		val startDate = LocalDate.now()

		val dependencyTask = mock(Task::class.java)
		val dependencyTaskDuration = 2
		given(dependencyTask.dependencies)
				.willReturn(Collections.emptyList())
		given(dependencyTask.dayDuration)
				.willReturn(dependencyTaskDuration)

		val task = mock(Task::class.java)
		val taskDuration = 1
		given(task.dependencies)
				.willReturn(Arrays.asList(dependencyTask))
		given(task.dayDuration)
				.willReturn(taskDuration)
		val endDate = startDate.plusDays((dependencyTaskDuration + taskDuration).toLong())

		given(taskService.fetchAllByPlan(plan))
				.willReturn(Arrays.asList(dependencyTask, task))

		val result = service.create(plan, startDate)

		val scheduledDependencyTask = result.tasks[dependencyTask]!!
		val scheduledTask = result.tasks[task]!!
		assertEquals(scheduledDependencyTask.endDate, scheduledTask.startDate)
		assertEquals(endDate, scheduledTask.endDate)
	}
}