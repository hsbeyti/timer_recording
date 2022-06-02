package hsbeyti.time.recording.controller;

import  hsbeyti.time.recording.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WorkingTimeControllerExceptionHandler {
	 @ExceptionHandler(NoExistingWorkTimeException.class)
	 @ResponseStatus(HttpStatus.NOT_FOUND)
	    public void handleoExistingWorkTim() {
	    }

}
