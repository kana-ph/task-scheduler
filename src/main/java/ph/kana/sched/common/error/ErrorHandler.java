package ph.kana.sched.common.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ErrorHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception) {
		return new ErrorResponse("RESOURCE_NOT_FOUND", "Cannot find resource of given ID");
	}

	@ExceptionHandler(MissingRequiredFieldException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public @ResponseBody ErrorResponse handleMissingRequiredFieldException(MissingRequiredFieldException exception) {
		String message = String.format("Missing required value for field: '%s'", exception.getField());
		return new ErrorResponse("MISSING_REQUIRED_VALUE", message);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
		String message = "Invalid JSON payload. Check for invalid data types, refer to docs for more information.";
		return new ErrorResponse("INVALID_JSON_BODY", message);
	}

	@ExceptionHandler(ServiceValidationException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public @ResponseBody ErrorResponse handleServiceValidationException(ServiceValidationException e) {
		ApiError error = e.getError();
		return new ErrorResponse(error.name(), error.getMessage());
	}

	@ExceptionHandler(InvalidTaskDependencyException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public @ResponseBody ErrorResponse handleInvalidTaskDependencyException(InvalidTaskDependencyException e) {
		return new ErrorResponse("INVALID_TASK_DEPENDENCY_ID", "One or more 'taskDependencyIds' invalid.");
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponse handleException(Exception e) {
		log.error(e.getMessage(), e);
		return new ErrorResponse("INTERNAL_SERVER_ERROR", "Error has been logged. Please contact admin.");
	}
}
