package hsbeyti.time.recording.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(code = NOT_FOUND, reason = "Wrokingtime Document Not found",value = NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
public UserNotFoundException(String message) {
	super (message);
}
}
