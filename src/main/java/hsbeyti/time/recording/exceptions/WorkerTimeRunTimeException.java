package hsbeyti.time.recording.exceptions;

public class WorkerTimeRunTimeException extends RuntimeException {

	public WorkerTimeRunTimeException(String wrokerName) {
	    super("Could not find Wroker  " + wrokerName);
	  }
	
}
