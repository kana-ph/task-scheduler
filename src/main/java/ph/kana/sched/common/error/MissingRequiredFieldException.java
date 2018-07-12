package ph.kana.sched.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Value missing for field: {field}")
public class MissingRequiredFieldException extends Exception {

	@Getter
	private final String field;

	public MissingRequiredFieldException(String field) {
		this.field = field;
	}
}
