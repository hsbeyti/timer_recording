package hsbeyti.time.recording.time.date.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeNowCreator implements TimeFormater {
	public final String TIME_FORMAT = "HH:mm:ss";
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
	public String getTimeNow(LocalDateTime timeNow) {
		DateTimeFormatter myFormatTime = DateTimeFormatter.ofPattern(TIME_FORMAT);
		return timeNow.format(myFormatTime);
	}

}
