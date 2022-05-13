package hsbeyti.time.recording.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String wrokerName) {
	    super("Could not find Wroker  " + wrokerName);
	  }
	
	public UserNotFoundException(String wrokerName,int code) {
	    super("Dockumtn allready exist  " + wrokerName);
	  }
	
}
