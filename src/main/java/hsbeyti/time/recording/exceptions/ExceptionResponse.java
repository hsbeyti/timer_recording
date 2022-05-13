package hsbeyti.time.recording.exceptions;

import java.util.Date;

public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private String details;

	public ExceptionResponse(java.util.Date timestampx, String message, String details) {
		
		this.timestamp = timestampx;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}
