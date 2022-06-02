package hsbeyti.time.recording.controller;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hsbeyti.time.recording.entities.*;
import hsbeyti.time.recording.services.TimeRecordingServices;
import hsbeyti.time.recording.services.TimeRecordingServicesInterface;

@RestController
@RequestMapping("/api/v1/timerecording")

public class WorkingTimeController {

	@Autowired
	TimeRecordingServicesInterface timeRecordingServices;
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/workingtime")//createTimeRecording
	public ResponseEntity<WorkingTime> createTimeRecording(@RequestBody WorkingTime WorkingTimeOnAProject) {
		//return timeRecordingServices.saveTimeRecording(WorkingTimeOnAProject);
		ResponseEntity<WorkingTime> response=timeRecordingServices.createTimeRecording(WorkingTimeOnAProject);
	     return response;
	  }

	
	@GetMapping("/workingtime/{worker}/{project}")
	public WorkingTime getWorkingTimeFor(@PathVariable String worker,@PathVariable String project) {
		return timeRecordingServices.getWorkingTimeFor(worker,/* Working on */project);
	}
	
	@PutMapping("/workingtime/workingbreakslot/{worker}/{project}")
	public ResponseEntity<WorkingTime> updateBreakTimeSloFor(@PathVariable String worker,/* Working on */ @PathVariable String project,
			@RequestBody BreakTimeSlot aNewBreakTimeSlot){
		return timeRecordingServices.updateBreakTimeSlotFor(worker,project,aNewBreakTimeSlot);
	
	}
	
	@PutMapping("/workingtime/workingtimeslot/{worker}/{project}")
	public ResponseEntity<WorkingTime> updateTimeSloFor(@PathVariable String worker,/* Working on */ @PathVariable String project,
			@RequestBody WorkingTimeSlot aNewTimeSlot){
		return timeRecordingServices.updateTimeSlotFor(worker,project,aNewTimeSlot);
	
	}	
}
