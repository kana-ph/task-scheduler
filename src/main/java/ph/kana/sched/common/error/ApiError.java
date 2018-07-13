package ph.kana.sched.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiError {

	PLAN_NAME_EXISTS("Plan with that given name already exists"),
	DURATION_NOT_POSITIVE("Field 'dayDuration' must be positive non-zero number.");

	private final String message;
}
