package hsbeyti.time.recording.entities;

import java.time.LocalDateTime;
import java.util.List;

public class WorkingDay {
	private LocalDateTime workingDay;
	private List<WorkingTimeStot> workingTimeSlots;
	private List<BreakTimeSlot> workingBreaks;

	public LocalDateTime getWorkingDay() {
		return workingDay;
	}

	public void setWorkingDay(LocalDateTime workingDay) {
		this.workingDay = workingDay;
	}

	public List<WorkingTimeStot> getWorkingTimeSlots() {
		return workingTimeSlots;
	}

	public void setWorkingTimeSlots(List<WorkingTimeStot> workingTimeSlots) {
		this.workingTimeSlots = workingTimeSlots;
	}

	public List<BreakTimeSlot> getWorkingBreaks() {
		return workingBreaks;
	}

	public void setWorkingBreaks(List<BreakTimeSlot> workingBreaks) {
		this.workingBreaks = workingBreaks;
	}

}
