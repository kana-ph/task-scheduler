package ph.kana.sched.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ph.kana.sched.plan.ProjectPlan;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
	@SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
	private Long id;

	@Version
	@NotNull
	@JsonIgnore
	private Long version;

	@NotNull
	private String name;

	@JoinColumn(name = "plan_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private ProjectPlan plan;

	@NotNull
	private int dayDuration;

	@ManyToMany(targetEntity = Task.class)
	private List<Task> dependencies;
}
