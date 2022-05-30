package hsbeyti.time.recording.services;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import hsbeyti.time.recording.entities.*;
import hsbeyti.time.recording.exceptions.*;
import hsbeyti.time.recording.repository.*;
import hsbeyti.time.recording.time.date.utility.DateFormater;
import hsbeyti.time.recording.time.date.utility.TodayDateCreator;

@Service
public class TimeRecordingServices {
	Logger logger = LogManager.getLogger(TimeRecordingServices.class);
	private String toDayDateString;
	@Autowired
	private AWrokingDay aworkingDay;
	@Autowired
	TimeRecordingRepository timeRecordingRepository;

	public String getdocumentFoundMessage(String worker, String project) {
		return "Document found " + worker + "on Project " + project;
	}

	public String getdocumentNotFoundMessage(String worker, String project) {
		return "Document Not found " + worker + "on Project " + project;
	}

	public String getdocumentUpdatedMessage(String worker, String project) {
		return "Document updated for " + worker + "on Project " + project;
	}

	// Executed by @PostMapping("/workingtime") in the controller class
	public ResponseEntity<WorkingTime> createTimeRecording(WorkingTime WorkingTimeOnAProject) {
		String workerName = WorkingTimeOnAProject.getCoWorkerName();
		String projectName = WorkingTimeOnAProject.getProjectName();
		String errorMessage = "Document exsists for " + workerName + " working on Project " + projectName;

		// Avoiding a duplicate document
		WorkingTime workingTime = timeRecordingRepository.findByCoWorkerNameAndProjectName(workerName, projectName);
		if (workingTime != null)
			throw new UserAllreadExistException(errorMessage);
		logger.debug("Saving a new document ");
		workingTime = saveWorkingTimeDocument(WorkingTimeOnAProject,
				"Saved successfully this new document: " + workingTime);

		return new ResponseEntity(workingTime, HttpStatus.CREATED);

	}

	// Helper function
	public WorkingTime saveWorkingTimeDocument(WorkingTime WorkingTimeOnAProject, String message) {
		WorkingTime workingTime = timeRecordingRepository.save(WorkingTimeOnAProject);
		logger.debug(message);
		return workingTime;
	}

	// Executed by @GetMapping("/workingtime/{worker}/{project}")
	public WorkingTime getWorkingTimeFor(String worker, /* Working on */ String project) {
		return isThereADocumentFor(worker, project);

	}

	// Helper function
	public WorkingTime isThereADocumentFor(String wroker_name, /* Working on */ String project_name) {
		WorkingTime workingTime = timeRecordingRepository.findByCoWorkerNameAndProjectName(wroker_name, project_name);
		if (workingTime == null)
			throw new UserNotFoundException(getdocumentNotFoundMessage(wroker_name, project_name));
		return workingTime;
	}

	// Executed by @PutMapping("/workingtime/workingbreakslot/{worker}/{project}")
	public ResponseEntity<WorkingTime> updateBreakTimeSlotFor(String worker, /* Working on */ String project,
			/* and having a break of */BreakTimeSlot aNewBreakTimeSlot) {

		// We can only update an existing document
		WorkingTime workingTimeDocument = isThereADocumentFor(worker, project);

		logger.debug(getdocumentFoundMessage(worker, project));

		toDayDateString = getTodaDate();

		// Check if the retrieved document contains toDayDateString entry  
		WorkingDay workingDay = containsThisWorkingDay(toDayDateString, workingTimeDocument.getWrokingDays());
		if (workingDay != null) {// it contains ):
			// add the new aNewBreakTimeSlot to the document
			workingDay.getWorkingBreaks().add(aNewBreakTimeSlot);
			saveWorkingTimeDocument(workingTimeDocument, getdocumentUpdatedMessage(worker, project) + toDayDateString);

			return new ResponseEntity(workingTimeDocument, HttpStatus.OK);

		} else {// Add first a new workingDay (with today date) to this document and then
				// add the aNewBreakTimeSlot
			return updateBreakTimeSlotFor(workingTimeDocument, aNewBreakTimeSlot);
		}
	}

	public ResponseEntity<WorkingTime> updateBreakTimeSlotFor(WorkingTime workingTimeDocument,
			BreakTimeSlot aNewBreakTimeSlot) {
		// a working day needs an array of Break time slot
		List<BreakTimeSlot> aBreakTimeSlots = createBreakTimeSlots();
		aBreakTimeSlots.add(aNewBreakTimeSlot); // needs also an array of working time slots
		WorkingDay aWorkingDay = createAWorkingDay(toDayDateString, aBreakTimeSlots, createWorkingTimeSlots());
		workingTimeDocument.getWrokingDays().add(aWorkingDay);
		WorkingTime workingTime = saveWorkingTimeDocument(workingTimeDocument,
				"Document updated for" + aWorkingDay + "with the break time slot: " + aNewBreakTimeSlot);

		return new ResponseEntity(workingTime, HttpStatus.OK);

	}

	// Executed by @PutMapping("/workingtime/workingtimeslot/{worker}/{project}")
	public ResponseEntity<WorkingTime> updateTimeSlotFor(String worker, /* Worked on the */ String project,
			/* for a duration of */WorkingTimeSlot aNewTimeSlot) {

		// we can only update existing document
		WorkingTime workingTimeDocument = isThereADocumentFor(worker, project);

		// get today WorkingDay
		toDayDateString = getTodaDate();

		WorkingDay workingDay = containsThisWorkingDay(toDayDateString, workingTimeDocument.getWrokingDays());
		if (workingDay != null) { // it contains ):
			// add new WorkingTimeSlot to to this document and then
			// add the WorkingTimeSlot
			workingDay.getWorkingTimeSlots().add(aNewTimeSlot);
			saveWorkingTimeDocument(workingTimeDocument, getdocumentUpdatedMessage(worker, project) + workingDay);

			return new ResponseEntity(workingTimeDocument, HttpStatus.OK);
		} else {// add first a new workingDay to the document
			return updateWorkingTimeSlot(workingTimeDocument, aNewTimeSlot);
		}
	}

	public WorkingDay containsThisWorkingDay(String toDayDateString, List<WorkingDay> workingDays) {

		return aworkingDay.containsThis(toDayDateString, workingDays);

	}
// Helper function
	public ResponseEntity<WorkingTime> updateWorkingTimeSlot(WorkingTime workingTimeDocument,
			WorkingTimeSlot aNewTimeSlot) {

		List<WorkingTimeSlot> aWorkingTimeSlot = createWorkingTimeSlots();
		aWorkingTimeSlot.add(aNewTimeSlot);
		WorkingDay aWorkingDay = createAWorkingDay(toDayDateString, createBreakTimeSlots(), aWorkingTimeSlot);
		workingTimeDocument.getWrokingDays().add(aWorkingDay);
		saveWorkingTimeDocument(workingTimeDocument,
				"Document updated for" + aWorkingDay + "with the wrorking time slot: " + aNewTimeSlot);

		return new ResponseEntity(workingTimeDocument, HttpStatus.OK);

	}

	public String getTodaDate() {
		DateFormater getToDayDate = new TodayDateCreator(LocalDateTime.now());
		return getToDayDate.getTodayDateString();
	}

	public WorkingDay createAWorkingDay(String todayDateString, List<BreakTimeSlot> aBreakTimeSlot,
			List<WorkingTimeSlot> aWorkingTimeSlot) {

		WorkingDay aWorkingDay = new WorkingDay();
		aWorkingDay.setWorkingDay(todayDateString);
		aWorkingDay.setWorkingBreaks(aBreakTimeSlot);
		aWorkingDay.setWorkingTimeSlots(aWorkingTimeSlot);
		return aWorkingDay;
	}

	public List<BreakTimeSlot> createBreakTimeSlots() {
		return new ArrayList<BreakTimeSlot>();

	}

	public List<WorkingTimeSlot> createWorkingTimeSlots() {
		return new ArrayList<WorkingTimeSlot>();

	}
}
