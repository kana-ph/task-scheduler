package ph.kana.sched.plan

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import ph.kana.sched.common.error.ServiceValidationException

@SpringBootTest
class ProjectPlanServiceTest {

	private val projectPlanRepository: ProjectPlanRepository = mock(ProjectPlanRepository::class.java)

	private lateinit var service: ProjectPlanService

	@Before
	fun setup() {
		service = ProjectPlanService(projectPlanRepository)
	}

	@Test
	fun `create should return the saved plan`() {
		val plan: ProjectPlan = mock(ProjectPlan::class.java)
		val planName = "plan name"

		given(plan.name)
				.willReturn(planName)
		given(projectPlanRepository.existsByName(planName))
				.willReturn(false)
		given(projectPlanRepository.save(plan))
				.willReturn(plan)

		val result = service.create(plan)

		assertEquals(plan, result)
	}

	@Test(expected = ServiceValidationException::class)
	fun `create should throw service exception when the plan given name already exists`() {
		val plan: ProjectPlan = mock(ProjectPlan::class.java)
		val planName = "plan name"

		given(plan.name)
				.willReturn(planName)
		given(projectPlanRepository.existsByName(planName))
				.willReturn(true)

		service.create(plan)
	}
}