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

	// @PostMapping("/workingtime")
	public ResponseEntity<WorkingTime> createTimeRecording(WorkingTime WorkingTimeOnAProject) {
		String workerName = WorkingTimeOnAProject.getCoWorkerName();
		String projectName = WorkingTimeOnAProject.getProjectName();
		String errorMessage = "Document exsists for " + workerName + " working on Project " 
		         + projectName;

		// Avoinding duplicate document
		WorkingTime workingTime = timeRecordingRepository.findByCoWorkerNameAndProjectName(workerName, projectName);
		if (workingTime != null)
			throw new UserAllreadExistException(errorMessage);
		logger.debug("Saving a new document ");
		workingTime = timeRecordingRepository.save(WorkingTimeOnAProject);
		logger.debug("Saved successfully a new document: " + workingTime);
		return new ResponseEntity(workingTime, HttpStatus.CREATED);

	}

	

	public String getdocumentFoundMessage(String worker, String project) {
		return "document found " + worker + "on Project " + project;
	}

	public String getdocumentNotFoundMessage(String worker, String project) {
		return "document Not found " + worker + "on Project " + project;
	}

	public String getdocumentUpdatedMessage(String worker, String project) {
		return "document updated for " + worker + "on Project " + project;
	}

	public WorkingTime saveTimeRecording(WorkingTime WorkingTimeOnAProject) {
		WorkingTime workingTime = timeRecordingRepository.save(WorkingTimeOnAProject);
		logger.debug("Saveded successfully a new  document: " + workingTime);
		return workingTime;
	}

	// @GetMapping("/workingtime/{worker}/{project}")
	public WorkingTime getWorkingTimeFor(String worker, /* Working on */ String project) {
		return isThereADocumentFor(worker, project);

	}
	public WorkingTime isThereADocumentFor(String wroker_name, /* Working on */ String project_name) {
		WorkingTime workingTime = timeRecordingRepository.findByCoWorkerNameAndProjectName(wroker_name, project_name);
		if (workingTime == null)
			throw new UserNotFoundException(getdocumentNotFoundMessage(wroker_name, project_name));
		return workingTime;
	}
	// @PutMapping("/workingtime/workingbreakslot/{worker}/{project}")
	public ResponseEntity<WorkingTime> updateBreakTimeSlotFor(String worker, /* Working on */ String project,
			/* and having a break of */BreakTimeSlot aNewBreakTimeSlot) {

		// we can only update existing document
		WorkingTime workingTimeDocument = isThereADocumentFor(worker, project);

		logger.debug(getdocumentFoundMessage(worker, project));

		toDayDateString = getTodaDate();

		// check if this document contains such toDayDateString
		WorkingDay workingDay = containsThisWorkingDay(toDayDateString, workingTimeDocument.getWrokingDays());
		if (workingDay != null) {// it contains ):
			// add new BreakTiemSlot to
			workingDay.getWorkingBreaks().add(aNewBreakTimeSlot);
			timeRecordingRepository.save(workingTimeDocument);
			logger.debug(getdocumentUpdatedMessage(worker, project) + toDayDateString);
			return new ResponseEntity(workingTimeDocument, HttpStatus.OK);

		} else {// add first a new workingDay (with today date) to this document and then
				// add the aNewBreakTimeSlot
			return updateBreakTimeSlotFor(workingTimeDocument, aNewBreakTimeSlot);
		}
	}

	public ResponseEntity<WorkingTime> updateBreakTimeSlotFor(WorkingTime workingTimeDocument,
			BreakTimeSlot aNewBreakTimeSlot) {
		// a wroking day needs an array of Break time slot
		List<BreakTimeSlot> aBreakTimeSlots = createBreakTimeSlots();
		aBreakTimeSlots.add(aNewBreakTimeSlot); // needs also an array of working time slots
		WorkingDay aWorkingDay = createAWorkingDay(toDayDateString, aBreakTimeSlots, createWorkingTimeSlots());
		workingTimeDocument.getWrokingDays().add(aWorkingDay);
		WorkingTime workingTime = timeRecordingRepository.save(workingTimeDocument);
		logger.debug("Document updated for" + aWorkingDay + "with the break time slot: " + aNewBreakTimeSlot);
		return new ResponseEntity(workingTime, HttpStatus.OK);

	}

	// @PutMapping("/workingtime/workingtimeslot/{worker}/{project}")
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
			timeRecordingRepository.save(workingTimeDocument);
			logger.debug(getdocumentUpdatedMessage(worker, project) + workingDay);
			return new ResponseEntity(workingTimeDocument, HttpStatus.OK);
		} else {// add first a new workingDay to the document
			return updateWorkingTimeSlot(workingTimeDocument, aNewTimeSlot);
		}
	}

	public WorkingDay containsThisWorkingDay(String toDayDateString, List<WorkingDay> workingDays) {

		return aworkingDay.containsThis(toDayDateString, workingDays);

	}

	public ResponseEntity<WorkingTime> updateWorkingTimeSlot(WorkingTime workingTimeDocument,
			WorkingTimeSlot aNewTimeSlot) {

		List<WorkingTimeSlot> aWorkingTimeSlot = createWorkingTimeSlots();
		aWorkingTimeSlot.add(aNewTimeSlot);
		WorkingDay aWorkingDay = createAWorkingDay(toDayDateString, createBreakTimeSlots(), aWorkingTimeSlot);
		workingTimeDocument.getWrokingDays().add(aWorkingDay);
		timeRecordingRepository.save(workingTimeDocument);
		logger.debug("Document updated for" + aWorkingDay + "with the wrorking time slot: " + aNewTimeSlot);
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
