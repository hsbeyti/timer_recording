package hsbeyti.time.recording.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import hsbeyti.time.recording.entities.*;
import hsbeyti.time.recording.exceptions.*;
import hsbeyti.time.recording.initilization.WorkingTimeInitialisationInterface;
import hsbeyti.time.recording.initilization.WorkingTimeInitializationImpl;
import hsbeyti.time.recording.repository.TimeRecordingRepository;
import hsbeyti.time.recording.timerecording.TimerecordingApplication;

@SpringBootTest(classes = TimerecordingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class TimeRecordingServicesTest {

	@Mock
	TimeRecordingRepository timeRecordingRepository;
	@Mock
	AWrokingDay aWorkingDay;
	@InjectMocks
	private TimeRecordingServices timeRecordingServices;

	private WorkingTime workingTime;
	private WorkingTimeInitialisationInterface workingTimeInitializationImpl;
	private WorkingDay workingDay;

	@BeforeEach
	public void setup() {

		workingTimeInitializationImpl = new WorkingTimeInitializationImpl();
		workingTime = workingTimeInitializationImpl.createAWorkingTimeObject("testWorker", "testProject", "22.02.2022");
	}

	@DisplayName("JUnit test for succefully saving a WorkingTime document ")
	@Test
	public void givenWorkingTimeObject_whenSaveWorkingTime_thenReturnWorkingTimeObject() throws IOException {

		// given -
		// avoid duplicate document in the DB
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(null);
		BDDMockito.given(timeRecordingRepository.save(workingTime)).willReturn(workingTime);
		// when - creating a new document
		ResponseEntity<WorkingTime> savedWorkingTime = timeRecordingServices.createTimeRecording(workingTime);

		// then - it must return HttpStatus.CREATED
		assertEquals(HttpStatus.CREATED, savedWorkingTime.getStatusCode());
	}

	@DisplayName("JUnit test for failing to save a WorkingTime Document because it is allready exist ")
	@Test
	public void givenWorkingTimeObject_whenSaveWorkingTime_thenReturnBadRequest() throws IOException {

		// given - Assuming that this document is alleardy saved in previous post action
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(workingTime);
		// when - creating a new document
		assertThrows(UserAllreadExistException.class, () -> {
			timeRecordingServices.createTimeRecording(workingTime);
		});

		// then - it must not be save a new WrokingTime document
		verify(timeRecordingRepository, never()).save(workingTime);
	}

	@DisplayName("JUnit test for successfully retrieving a WorkingTime based on worker and project names ")
	@Test
	public void givenAWorkerAndAProject_whenSearchingforADocument_thenReturnWorkingTimeObject() throws IOException {

		// given -
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(workingTime);
		// when - creating a new document
		WorkingTime savedOne = timeRecordingServices.isThereADocumentFor(workingTime.getCoWorkerName(),
				workingTime.getProjectName());

		// then - it must return HttpStatus.CREATED
		assertNotNull(savedOne);
	}

	@DisplayName("JUnit test for failing to retrieve a WorkingTime document based on worker and project names ")
	@Test
	public void givenAWorkerAndAProject_whenSearchingforADocument_thenReturnNul() throws IOException {

		// given -
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(null);

		// when - creating a new document
		assertThrows(UserNotFoundException.class, () -> {
			timeRecordingServices.isThereADocumentFor(workingTime.getCoWorkerName(), workingTime.getProjectName());
		});

	}

	// update WorkingTimetFor
	@DisplayName("JUnit test for failing to update an existing WorkingTime  ")
	@Test
	public void givenAWorkerAprojectAndAworkingTimeSlot_whenUpdatingNotExistingDocument_thenThrowException()
			throws IOException {

		// given -no document found to update
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(null);
		// when - updating document
		assertThrows(UserNotFoundException.class, () -> {
			timeRecordingServices.updateTimeSlotFor(workingTime.getCoWorkerName(), workingTime.getProjectName(),
					workingTimeInitializationImpl.getWorkingTimeCreatedTemplate().getWorkingTimeSlot());
		});

		// then - it must not update WrokingTime document
		verify(timeRecordingRepository, never()).save(workingTime);
	}

	// update WorkingTimeSlotFor
	@DisplayName("JUnit test for successfully Updating aWorkingTime documnt with an existing working day with a new  WorkingTimeSlot  ")
	@Test
	public void givenAWorkerAprojectAndAworkingTimeSlot_whenUpdatingExistingDocument_thenReturnDocumentObject()
			throws IOException {

		// given - to be updated Document found
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(workingTime);
		// given - the document contains this working day
		BDDMockito.given(aWorkingDay.containsThis("20.2.222", workingTime.getWrokingDays())).willReturn(workingDay);
		// when - add a new workingTimeSlot to this document
		ResponseEntity<WorkingTime> savedOne = timeRecordingServices.updateTimeSlotFor(workingTime.getCoWorkerName(),
				workingTime.getProjectName(),
				workingTimeInitializationImpl.getWorkingTimeCreatedTemplate().getWorkingTimeSlot());

		// then - it must return HttpStatus.CREATED
		assertEquals(HttpStatus.OK, savedOne.getStatusCode());

	}

	// update WorkingTimeSlotFor
	@DisplayName("JUnit test for successfully Updating aWorkingTime documnt with a new working day with a new  WorkingTimeSlot  ")
	@Test
	public void givenAWorkerAprojectAndAworkingTimeSlotAndAworkingDay_whenUpdatingExistingDocument_thenReturnDocumentObject()
			throws IOException {

		// given - to be updated Document found
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(workingTime);
		// given - the document does not contains this working day
		BDDMockito.given(aWorkingDay.containsThis("20.2.222", workingTime.getWrokingDays())).willReturn(null);
		// when - add a new workingTimeSlot to this document
		ResponseEntity<WorkingTime> savedOne = timeRecordingServices.updateTimeSlotFor(workingTime.getCoWorkerName(),
				workingTime.getProjectName(),
				workingTimeInitializationImpl.getWorkingTimeCreatedTemplate().getWorkingTimeSlot());

		// then - it must return HttpStatus.CREATED
		assertEquals(HttpStatus.OK, savedOne.getStatusCode());

	}

	// update BreakTimeSlotFor
	@DisplayName("JUnit test for successfully Updating an existing WorkingTime ")
	@Test
	public void givenAWorkerAprojectAndABreakTimeSlot_whenUpdatingExistingDocument_thenReturnDocumentObject()
			throws IOException {

		// given -
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(workingTime);
		// given -
		BDDMockito.given(aWorkingDay.containsThis("", workingTime.getWrokingDays())).willReturn(workingDay);
		// when - updating a new document
		ResponseEntity<WorkingTime> savedOne = timeRecordingServices.updateBreakTimeSlotFor(
				workingTime.getCoWorkerName(), workingTime.getProjectName(),
				workingTimeInitializationImpl.getWorkingTimeCreatedTemplate().getBreakTimeSlot());

		// then - it must return HttpStatus.CREATED
		assertEquals(HttpStatus.OK, savedOne.getStatusCode());

	}

	// update BreakTimetFor
	@DisplayName("JUnit test for failing to update an existing WorkingTime  ")
	@Test
	public void givenAWorkerAprojectAndABreakTimeSlot_whenUpdatingNotExistingDocument_thenThrowException()
			throws IOException {

		// given -
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(null);
		// when - creating a new document
		assertThrows(UserNotFoundException.class, () -> {
			timeRecordingServices.updateBreakTimeSlotFor(workingTime.getCoWorkerName(), workingTime.getProjectName(),
					workingTimeInitializationImpl.getWorkingTimeCreatedTemplate().getBreakTimeSlot());
		});

		// then - it must not be to save a new WrokingTime document
		verify(timeRecordingRepository, never()).save(workingTime);
	}

}
