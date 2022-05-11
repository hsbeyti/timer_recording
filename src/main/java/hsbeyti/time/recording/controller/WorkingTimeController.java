package hsbeyti.time.recording.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
	
	@PostMapping("/create/new")
	public WorkingTime createNewWorkingDocument(@RequestBody WorkingTime WorkingTimeOnAProject) {
		return timeRecordingServices.createTimeRecording(WorkingTimeOnAProject);
	}
}
