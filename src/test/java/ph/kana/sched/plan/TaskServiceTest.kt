package ph.kana.sched.plan

import org.junit.Before
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TaskServiceTest {

	private val taskRepository = mock(TaskRepository::class.java)

	private lateinit var service: TaskService

	@Before
	fun setup() {
		service = TaskService(taskRepository)
	}
}