package hsbeyti.time.recording.entities;

import java.util.List;

import org.springframework.stereotype.Component;


public class WorkingDayInWorkingDaysImpl implements WrokingDayInWorkingDays {
	private List<WorkingDay> workingDays;

	@Override
	public WorkingDay containsThisWorkignDay(String workingDay) {
		for (WorkingDay aWorkingDay : workingDays) {
			if (aWorkingDay.getWorkingDay().equals(workingDay))
				return aWorkingDay;
		}
		return null;
	}

	
	public List<WorkingDay> getworkingDaysy() {
		return workingDays;
	}

	public void setworkingDays(List<WorkingDay> workingDays) {
		this.workingDays = workingDays;
	}

}
