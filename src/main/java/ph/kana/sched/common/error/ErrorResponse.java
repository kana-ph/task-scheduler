package ph.kana.sched.common.error;

import lombok.*;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	private final String code;
	private final String message;
}
