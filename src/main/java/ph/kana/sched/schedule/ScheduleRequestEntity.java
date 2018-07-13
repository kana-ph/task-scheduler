package ph.kana.sched.schedule;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
class ScheduleRequestEntity {

	private Long planId;

	private LocalDate startDate;
}
