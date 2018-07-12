package ph.kana.sched.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceValidationException extends Exception {

	private final ApiError error;
}
