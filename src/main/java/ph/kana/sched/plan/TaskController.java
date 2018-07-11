package ph.kana.sched.plan;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/plan/{planId}/task")
public class TaskController {

	@GetMapping
	public @ResponseBody List<TaskRestEntity> showAll(@PathVariable Long planId) {
		return Collections.emptyList();
	}
}
