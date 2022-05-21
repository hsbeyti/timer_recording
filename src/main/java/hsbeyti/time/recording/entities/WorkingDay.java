package hsbeyti.time.recording.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

public class WorkingDay {
	@Field("working_day")
	private String workingDay;
	@Field("working_timeSlots")
	private List<WorkingTimeSlot> workingTimeSlots;
	@Field("working_breaks")
	private List<BreakTimeSlot> workingBreaks;
	

	public WorkingDay() {
		
	}

	public WorkingDay(String workingDay
		, List<WorkingTimeSlot> workingTimeSlots,
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

	public List<WorkingTimeSlot> getWorkingTimeSlots() {
		return workingTimeSlots;
	}

	public void setWorkingTimeSlots(List<WorkingTimeSlot> workingTimeSlots) {
		this.workingTimeSlots = workingTimeSlots;
	}

	public List<BreakTimeSlot> getWorkingBreaks() {
		return workingBreaks;
	}

	public void setWorkingBreaks(List<BreakTimeSlot> workingBreaks) {
		this.workingBreaks = workingBreaks;
	}
    
}
