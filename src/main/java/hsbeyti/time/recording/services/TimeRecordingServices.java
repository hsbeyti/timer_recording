package hsbeyti.time.recording.services;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import hsbeyti.time.recording.entities.*;
import hsbeyti.time.recording.exceptions.UserNotFoundException;
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

	public ResponseEntity<Object> createTimeRecording(WorkingTime WorkingTimeOnAProject) {
		// test if a document allready exist for this project and this co-worker
		// extract co-worker name
		// extract project name
		String workerName = WorkingTimeOnAProject.getCoWorkerName();
		String projectName = WorkingTimeOnAProject.getProjectName();
		Optional<WorkingTime> workingTime = isThereADocumentFor(workerName, projectName);
		// System.out.println(found);
		if (!workingTime.isPresent()) {

			logger.warn("creating a new one document ");
			WorkingTime savedWorkingTime = timeRecordingRepository.save(WorkingTimeOnAProject);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(savedWorkingTime.getCoWorkerName()).toUri();

			return ResponseEntity.created(location).build();
		} else {
			logger.warn("document allreday exsist");
			throw new UserNotFoundException("Document allreday exsist for " + workerName);
		}
	}

	public Optional<WorkingTime> isThereADocumentFor(String wroker_name, String project_name) {
		return timeRecordingRepository.findByCoWorkerNameAndProjectName(wroker_name, project_name);

	}

	public List<Optional<WorkingTime>> getWorkingTimeForA(String wrokerName) {
		List<Optional<WorkingTime>> workingTime = timeRecordingRepository.findByCoWorkerName(wrokerName);
		if (!workingTime.get(0).isPresent()) {
			logger.warn("document not found " + wrokerName);
			throw new UserNotFoundException("Document not found for " + wrokerName);

		}
		logger.warn("document  found " + wrokerName);
		return workingTime;
	}

	public Optional<WorkingTime> getWorkingTimeFor(String worker, String project) {
		Optional<WorkingTime> workingTime = isThereADocumentFor(worker, project);
		if (!workingTime.isPresent()) {
			logger.warn("document not found " + worker + "on Project " + project);
			throw new UserNotFoundException("Document not found for " + worker + "on Project " + project);

		}
		logger.warn("document  found " + worker + "on Project " + project);
		return workingTime;
	}

	public Optional<WorkingTime> updateBreakTimeSlotFor(String worker, String project,
			BreakTimeSlot aNewBreakTimeSlot) {
		// check in database if such a document exist
		Optional<WorkingTime> workingTimeDocument = isThereADocumentFor(worker, project);
		if (!workingTimeDocument.isPresent()) {
			logger.warn("document not found " + worker + "on Project " + project);
			throw new UserNotFoundException("Document not found for " + worker + "on Project " + project);

		}
		// document found in Database
		logger.warn("document  found " + worker + "on Project " + project);
		// get today WorkingDay
		toDayDateString = getTodaDate();
		// check if today date is allready exist in this document

		WorkingDay workingDay = aworkingDay.containsThis(toDayDateString, workingTimeDocument.get().getWrokingDays());
		if (workingDay != null) {
			// add new BreakTiemSLot to
			workingDay.getWorkingBreaks().add(aNewBreakTimeSlot);
			timeRecordingRepository.save(workingTimeDocument.get());
			return workingTimeDocument;
		} else {// add first a new workingDay to the document
			return updateBreakTimeSlot(workingTimeDocument, aNewBreakTimeSlot);
		}
	}

	public Optional<WorkingTime> updateBreakTimeSlot(Optional<WorkingTime> workingTimeDocument,
			BreakTimeSlot aNewBreakTimeSlot) {

		List<BreakTimeSlot> aBreakTimeSlots = createBreakTimeSlots();
		aBreakTimeSlots.add(aNewBreakTimeSlot);
		WorkingDay aWorkingDay = createAWorkingDay(toDayDateString, aBreakTimeSlots, createWorkingTimeSlots());
		workingTimeDocument.get().getWrokingDays().add(aWorkingDay);
		timeRecordingRepository.save(workingTimeDocument.get());
		return workingTimeDocument;

	}

	public Optional<WorkingTime> updateTimeSlotFor(String worker, String project, WorkingTimeSlot aNewTimeSlot) {
		// check in database if such a document exist
		Optional<WorkingTime> workingTimeDocument = isThereADocumentFor(worker, project);
		if (!workingTimeDocument.isPresent()) {
			logger.warn("document not found " + worker + "on Project " + project);
			throw new UserNotFoundException("Document not found for " + worker + "on Project " + project);

		}

		// get today WorkingDay
		toDayDateString = getTodaDate();

		WorkingDay workingDay = aworkingDay.containsThis(toDayDateString, workingTimeDocument.get().getWrokingDays());
		if (workingDay != null) {
			// add new BreakTiemSLot to
			workingDay.getWorkingTimeSlots().add(aNewTimeSlot);
			timeRecordingRepository.save(workingTimeDocument.get());
			return workingTimeDocument;
		} else {// add first a new workingDay to the document
			return updateWorkingTimeSlot(workingTimeDocument, aNewTimeSlot);
		}
	}

	public WorkingDay containsThisWorkingDaY(String toDayDateString, List<WorkingDay> workingDays) {

		return aworkingDay.containsThis(toDayDateString, workingDays);

	}

	public Optional<WorkingTime> updateWorkingTimeSlot(Optional<WorkingTime> workingTimeDocument,
			WorkingTimeSlot aNewTimeSlot) {

		List<WorkingTimeSlot> aWorkingTimeSlot = createWorkingTimeSlots();
		aWorkingTimeSlot.add(aNewTimeSlot);
		WorkingDay aWorkingDay = createAWorkingDay(toDayDateString, createBreakTimeSlots(), aWorkingTimeSlot);
		workingTimeDocument.get().getWrokingDays().add(aWorkingDay);
		timeRecordingRepository.save(workingTimeDocument.get());
		return workingTimeDocument;

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
