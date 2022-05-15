package hsbeyti.time.recording.controller;

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
import org.springframework.web.bind.annotation.RestController;

import hsbeyti.time.recording.entities.*;
import hsbeyti.time.recording.repository.TimeRecordingRepository;
import hsbeyti.time.recording.services.TimeRecordingServices;

@RestController
@RequestMapping("/api/v1/timerecording")

public class WorkingTimeController {

	@Autowired
	TimeRecordingServices timeRecordingServices;

	@PostMapping("/workingtime")
	public WorkingTime createTimeRecording(@RequestBody WorkingTime WorkingTimeOnAProject) {
		return timeRecordingServices.createTimeRecording(WorkingTimeOnAProject);
	}

	@GetMapping("/workingtime/{worker}")
	public List<Optional<WorkingTime>> getWorkingTimeForA(@Valid @PathVariable String worker) {
		return timeRecordingServices.getWorkingTimeForA(worker);
	}
	@GetMapping("/workingtime/{worker}/{project}")
	public Optional<WorkingTime> getWorkingTimeFor(@PathVariable String worker,@PathVariable String project) {
		return timeRecordingServices.getWorkingTimeFor(worker,project);
	}
	@PutMapping("/workingtime/workingbreakslot/{worker}/{project}")
	public Optional<WorkingTime> updateBreakTimeSloFor(@PathVariable String worker, @PathVariable String project,
			@RequestBody BreakTimeSlot aNewBreakTimeSlot){
		return timeRecordingServices.updateBreakTimeSlotFor(worker,project,aNewBreakTimeSlot);
	
	}
	
	@PutMapping("/workingtime/workingtimeslot/{worker}/{project}")
	public Optional<WorkingTime> updateTimeSloFor(@PathVariable String worker, @PathVariable String project,
			@RequestBody WorkingTimeSlot aNewTimeSlot){
		return timeRecordingServices.updateTimeSlotFor(worker,project,aNewTimeSlot);
	
	}
}
