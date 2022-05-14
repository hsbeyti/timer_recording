package hsbeyti.time.recording.entities;

import java.util.List;

import org.springframework.stereotype.Component;


public class AWrokingDayImpl implements AWrokingDay {
	private List<WorkingDay> workingDays;

	@Override
	public WorkingDay containsThisWorkignDay(String aworkingDay) {
		for (WorkingDay aWorkingDay : workingDays) {
			if (aWorkingDay.getWorkingDay().equals(aworkingDay))
				return aWorkingDay;
		}
		return null;
	}

	
	public AWrokingDayImpl(List<WorkingDay> workingDays) {
	
		this.workingDays = workingDays;
	}


	public List<WorkingDay> getworkingDays() {
		return workingDays;
	}

	public void setworkingDays(List<WorkingDay> workingDays) {
		this.workingDays = workingDays;
	}

}
