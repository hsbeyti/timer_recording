package hsbeyti.time.recording.services;

import java.net.URI;
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

@Service
public class TimeRecordingServices {
	Logger logger = LogManager.getLogger(TimeRecordingServices.class);
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
			WorkingTime savedWorkingTime = timeRecordingRepository.insert(WorkingTimeOnAProject);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(savedWorkingTime.getId()).toUri();

			return ResponseEntity.created(location).build();
		} else {
			logger.warn("document allreday exsist");
			throw new UserNotFoundException("Document allreday exsist",1);
		}
	}

	public Optional<WorkingTime> isThereADocumentFor(String wroker_name, String project_name) {
		return timeRecordingRepository.findByCoWorkerNameAndProjectName(wroker_name, project_name);

	}

	public List<Optional<WorkingTime>> getWorkingTime(String wrokerName) {
		List<Optional<WorkingTime>> workingTime = timeRecordingRepository.findByCoWorkerName(wrokerName);
		if (!workingTime.get(0).isPresent()) {
			logger.warn("document not found " + wrokerName);
			throw new UserNotFoundException(wrokerName);

		}
		logger.warn("document  found " + wrokerName);
		return workingTime;
	}

	public Optional<WorkingTime> getWorkingTimeForAWorkerOnAProject(String worker, String project) {
		Optional<WorkingTime> workingTime = isThereADocumentFor(worker, project);
		if (!workingTime.isPresent()) {
			logger.warn("document not found " + worker + "on Project " + project);
			throw new UserNotFoundException(worker + "on Project " + project);

		}
		logger.warn("document  found "+ worker + "on Project " + project);
		return workingTime;
	}
}
