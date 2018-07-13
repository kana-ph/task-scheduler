package ph.kana.sched.schedule

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.springframework.boot.test.context.SpringBootTest
import ph.kana.sched.common.error.InvalidPlanException
import ph.kana.sched.common.error.MissingRequiredFieldException
import ph.kana.sched.plan.ProjectPlan
import ph.kana.sched.plan.ProjectPlanService
import java.time.LocalDate
import java.util.*

@SpringBootTest
class ScheduleControllerTest {

	private val projectPlanService: ProjectPlanService = mock(ProjectPlanService::class.java)
	private val scheduleService: ScheduleService = mock(ScheduleService::class.java)

	private lateinit var controller: ScheduleController

	@Before
	fun setup() {
		controller = ScheduleController(projectPlanService, scheduleService)
	}

	@Test
	fun `create should create schedule when given request is valid`() {
		val plan: ProjectPlan = mock(ProjectPlan::class.java)
		val planId = 1L
		given(projectPlanService.fetchById(planId))
				.willReturn(Optional.of(plan))

		val startDate = LocalDate.now()
		val request = ScheduleRequestEntity()
		request.planId = planId
		request.startDate = startDate

		given(scheduleService.create(plan, startDate))
				.willReturn(mock(Schedule::class.java))

		controller.create(request)

		then(scheduleService)
				.should(times(1))
				.create(plan, startDate)
	}

	@Test(expected = MissingRequiredFieldException::class)
	fun `create should throw missing required field when plan ID is missing`() {
		val request = ScheduleRequestEntity()
		request.planId = null
		request.startDate = LocalDate.now()

		controller.create(request)
	}

	@Test(expected = MissingRequiredFieldException::class)
	fun `create should throw missing required field when start date is missing`() {
		val plan: ProjectPlan = mock(ProjectPlan::class.java)
		val planId = 1L
		given(projectPlanService.fetchById(planId))
				.willReturn(Optional.of(plan))

		val request = ScheduleRequestEntity()
		request.planId = planId
		request.startDate = null

		controller.create(request)
	}

	@Test(expected = InvalidPlanException::class)
	fun `create should throw invalid plan when given plan ID is invalid`() {
		given(projectPlanService.fetchById(anyLong()))
				.willReturn(Optional.empty())

		val startDate = LocalDate.now()
		val request = ScheduleRequestEntity()
		request.planId = 999
		request.startDate = startDate

		controller.create(request)
	}
}