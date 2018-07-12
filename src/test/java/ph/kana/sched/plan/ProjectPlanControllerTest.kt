package ph.kana.sched.plan

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.springframework.boot.test.context.SpringBootTest
import ph.kana.sched.common.error.MissingRequiredFieldException
import ph.kana.sched.common.error.ResourceNotFoundException
import java.util.*

@SpringBootTest
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

	@Test
	fun `create should create a plan object when the given properties are valid`() {
		val plan = ProjectPlan()
		plan.name = "plan name"

		controller.create(plan)

		then(projectPlanService)
				.should(times(1))
				.create(plan)
	}

	@Test(expected = MissingRequiredFieldException::class)
	fun `create should throw missing field exception when name is missing`() {
		val plan = ProjectPlan()
		plan.name = null

		controller.create(plan)
	}
}