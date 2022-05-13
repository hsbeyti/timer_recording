package hsbeyti.time.recording.services;

import java.net.URI;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import hsbeyti.time.recording.entities.*;
import hsbeyti.time.recording.exceptions.WorkerTimeRunTimeException;
import hsbeyti.time.recording.repository.*;
@Service
public class TimeRecordingServices {
	Logger logger = LogManager.getLogger(TimeRecordingServices.class);
	@Autowired
	TimeRecordingRepository timeRecordingRepository ;
	
	public ResponseEntity<Object> createTimeRecording (WorkingTime WorkingTimeOnAProject) {
		// test if  a document allready exist for this project and this co-worker
		// extract co-worker name
		// extract project name
		String workerName=WorkingTimeOnAProject.getCoWorkerName();
		String projectName=WorkingTimeOnAProject.getProjectName();
		WorkingTime found=isThereADocumentFor(workerName,projectName);
		//System.out.println(found);
		if(found==null){// not found then create a new one
			logger.warn("create a new one document ");
			WorkingTime savedWorkingTime = timeRecordingRepository.insert(WorkingTimeOnAProject);
		    URI location=ServletUriComponentsBuilder
		       .fromCurrentRequest()
		       .path("/{id}")
		       .buildAndExpand(savedWorkingTime.getId()).toUri();
		       
		    return  ResponseEntity.created(location).build();
		}
		 logger.warn("document allreday exsist");
	    return null;// document allready exsist
	}  
	public WorkingTime isThereADocumentFor(String wroker_name, String project_name) {
		WorkingTime found=timeRecordingRepository.findByCoWorkerNameAndProjectName(wroker_name,project_name);
		return found;
	}

	
	public Optional<WorkingTime>  getWorkingTime(String wrokerName)  {
		Optional<WorkingTime>  workingTime= timeRecordingRepository.findByCoWorkerName(wrokerName);
				if(!workingTime.isPresent()) {
					logger.warn("document not found "+ wrokerName);
					throw  new WorkerTimeRunTimeException(wrokerName);
				    
				}
				logger.warn("document  found "+ wrokerName);
	    return 	workingTime;	
	}
}
