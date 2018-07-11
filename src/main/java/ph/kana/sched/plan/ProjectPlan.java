package ph.kana.sched.plan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class ProjectPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_plan_sequence")
	@SequenceGenerator(name = "project_plan_sequence", sequenceName = "project_plan_sequence", allocationSize = 1)
	private Long id;

	@Version
	@NotNull
	@JsonIgnore
	private Long version;

	@NotNull
	private String name;
}
