package ph.kana.sched.task

import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.springframework.boot.test.context.SpringBootTest
import ph.kana.sched.common.error.ServiceValidationException

@SpringBootTest
class TaskServiceTest {

	private val taskRepository = mock(TaskRepository::class.java)

	private lateinit var service: TaskService

	@Before
	fun setup() {
		service = TaskService(taskRepository)
	}

	@Test
	fun `create should save the given then when valid`() {
		val task = Task()
		task.dayDuration = 1

		service.create(task)

		then(taskRepository)
				.should(times(1))
				.save(task)
	}

	@Test(expected = ServiceValidationException::class)
	fun `create should throw service validation when given duration is not positive`() {
		val task = Task()
		task.dayDuration = -1

		service.create(task)
	}
}