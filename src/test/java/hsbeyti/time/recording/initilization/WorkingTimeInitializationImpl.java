package hsbeyti.time.recording.initilization;

import java.util.ArrayList;
import java.util.List;

import hsbeyti.time.recording.entities.BreakTimeSlot;
import hsbeyti.time.recording.entities.WorkingDay;
import hsbeyti.time.recording.entities.WorkingTime;
import hsbeyti.time.recording.entities.WorkingTimeSlot;

public class WorkingTimeInitializationImpl implements WorkingTimeInitialisationInterface {
	private WorkingTimeInitializationTemplate workingTemplate;

	@Override
	public WorkingTimeInitializationTemplate getWorkingTimeCreatedTemplate() {
		// TODO Auto-generated method stub
		return workingTemplate;
	}
    // create a complete WorkingTime Object that represent a JSON document
	@Override
	public WorkingTime createAWorkingTimeObject(String workerName, String projectName,String date) {
		
		workingTemplate = new WorkingTimeInitializationTemplate(new WorkingTime(), new WorkingTimeSlot("70", "1000"),
				new WorkingDay(), new BreakTimeSlot("Lunch", "30"));

		List<WorkingTimeSlot> workingTimeSlots = new ArrayList<WorkingTimeSlot>();
		workingTimeSlots.add(workingTemplate.getWorkingTimeSlot());

		List<BreakTimeSlot> breakTimeSlots = new ArrayList<BreakTimeSlot>();
		breakTimeSlots.add(workingTemplate.getBreakTimeSlot());

		WorkingDay workingDay = workingTemplate.getWorkingDay();
		workingDay.setWorkingDay(date);
		workingDay.setWorkingTimeSlots(workingTimeSlots);
		workingDay.setWorkingBreaks(breakTimeSlots);

		WorkingTime workingTime = workingTemplate.getWorkingTime();
		workingTime.setCoWorkerName(workerName);
		workingTime.setProjectName(projectName);
		workingTime.setProjectOrderNumber("testOrder");
		workingTime.setWrokingDays(new ArrayList<WorkingDay>());
		workingTime.getWrokingDays().add(workingDay);

		return workingTemplate.getWorkingTime();
	}

}
