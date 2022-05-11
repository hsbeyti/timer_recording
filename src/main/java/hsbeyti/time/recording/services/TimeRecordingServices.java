package hsbeyti.time.recording.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hsbeyti.time.recording.entities.*;


import hsbeyti.time.recording.repository.*;
@Service
public class TimeRecordingServices {
	
	@Autowired
	TimeRecordingRepository timeRecordingRepository ;

	public WorkingTime createTimeRecording (WorkingTime WorkingTimeOnAProject) {
		// test if  a document allready exist for this project and this co-worker
		// extract co-worker name
		// extract project name
		String workerName=WorkingTimeOnAProject.getCoWorkerName();
		String projectName=WorkingTimeOnAProject.getProjectName();
		WorkingTime found=isThereADocumentFor(workerName,projectName);
		//System.out.println(found);
		if(found==null)// not found then creat a new one
		   return timeRecordingRepository.save(WorkingTimeOnAProject);
		 
	     return null;// document allreday exsist
	}  
	public WorkingTime isThereADocumentFor(String wroker_name, String project_name) {
		WorkingTime found=timeRecordingRepository.findByCoWorkerNameAndProjectName(wroker_name,project_name);
		return found;
	}

}
