package ph.kana.sched.common.error;

import lombok.Getter;

public enum ApiError {

	PLAN_NAME_EXISTS("Plan with that given name already exists");

	@Getter
	private final String message;

	ApiError(String message) {
		this.message = message;
	}
}
