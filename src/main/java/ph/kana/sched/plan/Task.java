package ph.kana.sched.plan;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Data
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
	@SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
	private Long id;

	@Version
	@NotNull
	private Long version;

	@NotNull
	private String name;

	@JoinColumn(name = "plan_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private ProjectPlan plan;

	@NotNull
	private int dayDuration;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "task_dependencies")
	private Collection<Task> dependencies;
}
