package hsbeyti.time.recording.controller;

import java.util.Optional;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import hsbeyti.time.recording.entities.*;
import hsbeyti.time.recording.repository.TimeRecordingRepository;
import hsbeyti.time.recording.services.TimeRecordingServices;
@RestController
@RequestMapping("/api/v1/timerecording")
@ComponentScan("hsbeyti.time.recording.*")
public class WorkingTimeController {

	@Autowired
	TimeRecordingServices timeRecordingServices;
	
	@PostMapping("/workingtime")
	public ResponseEntity<Object> createNewWorkingDocument(@RequestBody WorkingTime WorkingTimeOnAProject) {
		return timeRecordingServices.createTimeRecording(WorkingTimeOnAProject);
	}
	@GetMapping("/workingtime/{worker}")
	public Optional<WorkingTime> getWorkingTimeForA(@PathVariable  String worker) {
		return timeRecordingServices.getWorkingTime(worker);
	}
}
