package ph.kana.sched.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Invalid ID in URL")
public class ResourceNotFoundException extends Exception { }
