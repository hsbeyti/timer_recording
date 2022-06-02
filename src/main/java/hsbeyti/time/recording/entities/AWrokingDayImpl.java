package hsbeyti.time.recording.entities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class AWrokingDayImpl implements AWrokingDay {
	



	@Override
	
	public WorkingDay containsThis(String workingDay,List<WorkingDay> workingDays) {
		for (WorkingDay aWorkingDay : workingDays) {
			if (aWorkingDay.getWorkingDay().equals(workingDay))
				return aWorkingDay;
		}
		return null;
	}

	
	

}
