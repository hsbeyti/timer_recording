package hsbeyti.time.recording.services;

import org.springframework.http.ResponseEntity;

import hsbeyti.time.recording.entities.*;


public interface TimeRecordingServicesInterface {
	
	
	public ResponseEntity<WorkingTime> createTimeRecording(WorkingTime WorkingTimeOnAProject);
	public WorkingTime getWorkingTimeFor(String worker, /* Working on */ String project);
	public ResponseEntity<WorkingTime> updateBreakTimeSlotFor(String worker, /* Working on */ String project,
			/* and having a break of */BreakTimeSlot aNewBreakTimeSlot);
	public ResponseEntity<WorkingTime> updateTimeSlotFor(String worker, /* Worked on the */ String project,
			/* for a duration of */WorkingTimeSlot aNewTimeSlot) ;

}
