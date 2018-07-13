package ph.kana.sched.schedule

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import ph.kana.sched.task.TaskService

@SpringBootTest
class ScheduleServiceTest {

	private val taskService: TaskService = mock(TaskService::class.java)

	private lateinit var service: ScheduleService

	@Before
	fun setup() {
		service = ScheduleService(taskService)
	}

	@Test
	fun `create should create a schedule plan when inputs are valid`() {
		// TODO
	}
}