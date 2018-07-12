package ph.kana.sched.plan

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import ph.kana.sched.common.ResourceNotFoundException
import java.util.*

class ProjectPlanControllerTest {

	private val projectPlanService: ProjectPlanService = mock(ProjectPlanService::class.java)

	private lateinit var controller: ProjectPlanController

	@Before
	fun setup() {
		controller = ProjectPlanController(projectPlanService)
	}

	@Test
	fun `show should respond the correct plan given a valid id`() {
		val plan: ProjectPlan = mock(ProjectPlan::class.java)
		val planId = 1L

		given(projectPlanService.fetchById(planId))
				.willReturn(Optional.of(plan))

		val response = controller.show(planId)

		assertEquals(plan, response)
	}

	@Test(expected = ResourceNotFoundException::class)
	fun `show should throw ResourceNotFoundException when the given id is invalid`() {
		given(projectPlanService.fetchById(anyLong()))
				.willReturn(Optional.empty())

		controller.show(999L)
	}
}