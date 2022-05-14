package hsbeyti.time.recording.time.date.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class TimeNowCreator implements TimeFormater {
	//@Value("${time.recording.TIME_FORMAT}")
	private  String TIME_FORMAT= "HH:mm:ss";
	private LocalDateTime timeNow;

	public TimeNowCreator(LocalDateTime timeNow) {

		this.timeNow = timeNow;
	}

	public LocalDateTime getTimeNow() {
		return timeNow;
	}

	public void setTimeNow(LocalDateTime timeNow) {
		this.timeNow = timeNow;
	}

	@Override
	public String getTimeNowString() {
		DateTimeFormatter myFormatTime = DateTimeFormatter.ofPattern(TIME_FORMAT);
		return timeNow.format(myFormatTime).toString();
	}

}
