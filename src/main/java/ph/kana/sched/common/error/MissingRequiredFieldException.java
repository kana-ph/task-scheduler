package ph.kana.sched.common.error;

import lombok.Getter;

public class MissingRequiredFieldException extends Exception {

	@Getter
	private final String field;

	public MissingRequiredFieldException(String field) {
		this.field = field;
	}
}
