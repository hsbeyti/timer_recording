package hsbeyti.time.recording.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

public class WorkingDay {
	
	private String workingDay;

	private List<WorkingTimeStot> workingTimeSlots;
	
	private List<BreakTimeSlot> workingBreaks;
	

	public WorkingDay(String workingDay
		, List<WorkingTimeStot> workingTimeSlots,
		List<BreakTimeSlot> workingBreaks) {

		this.workingDay = workingDay;
		this.workingTimeSlots = workingTimeSlots;
		this.workingBreaks = workingBreaks;
	}

	public String getWorkingDay() {
		return workingDay;
	}

	public void setWorkingDay(String workingDay) {
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
