package hsbeyti.time.recording.entities;

import java.time.LocalTime;

public class WorkingTimeStot {
	private LocalTime start;
	private LocalTime end;

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

}
