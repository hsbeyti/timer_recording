package hsbeyti.time.recording.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hsbeyti.time.recording.entities.*;


import hsbeyti.time.recording.repository.*;
@Service
public class TimeRecordingServices {
	
	@Autowired
	TimeRecordingRepository timeRecordingRepository ;

	public CoWorkerWorkingTimeOnAProject createTimeRecording (CoWorkerWorkingTimeOnAProject coWorkerWorkingTimeOnAProject) {
		return timeRecordingRepository.save(coWorkerWorkingTimeOnAProject);
	}

}
