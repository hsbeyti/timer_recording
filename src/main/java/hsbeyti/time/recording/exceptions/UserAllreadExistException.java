package hsbeyti.time.recording.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAllreadExistException  extends RuntimeException{
	public UserAllreadExistException(String message) {
		super (message);
	}
}


