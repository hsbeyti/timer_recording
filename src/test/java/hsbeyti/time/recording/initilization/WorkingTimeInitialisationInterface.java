package hsbeyti.time.recording.initilization;

import hsbeyti.time.recording.entities.WorkingTime;

public interface WorkingTimeInitialisationInterface {
	
	public WorkingTime createAWorkingTimeObject(String workerName, String projectName);
	public WorkingTimeInitializationTemplate getWorkingTimeCreatedObject();

}
