package ph.kana.sched.plan

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.eq
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import ph.kana.sched.common.error.ResourceNotFoundException
import java.util.*

@SpringBootTest
class TaskControllerTest {

	private val projectPlanService: ProjectPlanService = mock(ProjectPlanService::class.java)
	private val taskService: TaskService = mock(TaskService::class.java)

	private lateinit var controller: TaskController

	@Before
	fun setup() {
		controller = TaskController(projectPlanService, taskService)
	}

	@Test
	fun `showAll should response list of task given a plan id`() {
		val plan: ProjectPlan = mock(ProjectPlan::class.java)
		val planId = 1L
		given(projectPlanService.fetchById(planId))
				.willReturn(Optional.of(plan))

		val task = mock(Task::class.java)
		val taskId = 1L
		given(task.id)
				.willReturn(taskId)
		val tasks: List<Task> = Arrays.asList(task)
		given(taskService.fetchAllByPlan(plan))
				.willReturn(tasks)

		val response = controller.showAll(planId)

		assertEquals(1, response.size)
		assertEquals(taskId, response[0].id)
	}

	@Test(expected = ResourceNotFoundException::class)
	fun `showAll should response resource not found when the given plan id is invalid`() {
		given(projectPlanService.fetchById(anyLong()))
				.willReturn(Optional.empty())

		controller.showAll(999)
	}

	@Test
	fun `show should response the task given valid plan and task ids`() {
		val plan: ProjectPlan = mock(ProjectPlan::class.java)
		val planId = 1L
		given(projectPlanService.fetchById(planId))
				.willReturn(Optional.of(plan))

		val task = mock(Task::class.java)
		val taskId = 1L
		given(task.id)
				.willReturn(taskId)
		given(taskService.fetchByPlanAndId(plan, taskId))
				.willReturn(Optional.of(task))

		val response = controller.show(planId, taskId)

		assertEquals(taskId, response.id)
	}

	@Test(expected = ResourceNotFoundException::class)
	fun `show should throw resource not found when given task id is invalid`() {
		val plan: ProjectPlan = mock(ProjectPlan::class.java)
		val planId = 1L
		given(projectPlanService.fetchById(planId))
				.willReturn(Optional.of(plan))

		given(taskService.fetchByPlanAndId(eq(plan), anyLong()))
				.willReturn(Optional.empty())

		controller.show(planId, 999)
	}

	@Test(expected = ResourceNotFoundException::class)
	fun `show should throw resource not found when given plan id is invalid`() {
		given(projectPlanService.fetchById(anyLong()))
				.willReturn(Optional.empty())

		controller.show(999, 999)
	}
}