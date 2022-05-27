package hsbeyti.time.recording.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import hsbeyti.time.recording.entities.*;
import hsbeyti.time.recording.exceptions.*;
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
	private WorkingTimeSlot workingTimeSlot;
	private WorkingDay WorkingDay;
	private BreakTimeSlot breakTimeSlot;

	@BeforeEach
	public void setup() {

		workingTimeSlot = new WorkingTimeSlot();
		workingTimeSlot.setStart("70");
		workingTimeSlot.setEnd("90");
		List<WorkingTimeSlot> workingTimeSlots = new ArrayList<WorkingTimeSlot>();
		workingTimeSlots.add(workingTimeSlot);

		breakTimeSlot = new BreakTimeSlot();
		breakTimeSlot.setDescription("Lunch");
		breakTimeSlot.setDuration("30");
		List<BreakTimeSlot> breakTimeSlots = new ArrayList<BreakTimeSlot>();
		breakTimeSlots.add(breakTimeSlot);

		workingTime = new WorkingTime();
		workingTime.setCoWorkerName("testWroker");
		workingTime.setProjectName("testProject");
		workingTime.setProjectOrderNumber("testOrder");
		workingTime.setWrokingDays(new ArrayList<WorkingDay>());
		WorkingDay = new WorkingDay();
		WorkingDay.setWorkingDay("26.05.2022");
		WorkingDay.setWorkingTimeSlots(workingTimeSlots);
		WorkingDay.setWorkingBreaks(breakTimeSlots);
		workingTime.getWrokingDays().add(WorkingDay);
	}

	@DisplayName("JUnit test for succefully saving a WorkingTime document ")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() throws IOException {

		// given -

		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(null);
		BDDMockito.given(timeRecordingRepository.save(workingTime)).willReturn(workingTime);
		// when - creating a new document
		ResponseEntity<WorkingTime> savedOne = timeRecordingServices.createTimeRecording(workingTime);

		// then - it must return HttpStatus.CREATED
		assertEquals(HttpStatus.CREATED, savedOne.getStatusCode());
	}

	@DisplayName("JUnit test for failing to save a WorkingTime that allready exist ")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnBadRequest() throws IOException {

		// given -
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(workingTime);
		// when - creating a new document
		assertThrows(UserAllreadExistException.class, () -> {
			timeRecordingServices.createTimeRecording(workingTime);
		});

		// then - it must not be to save a new WrokingTime document
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

		// given -
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(null);
		// when - creating a new document
		assertThrows(UserNotFoundException.class, () -> {
			timeRecordingServices.updateTimeSlotFor(workingTime.getCoWorkerName(), workingTime.getProjectName(),
					workingTimeSlot);
		});

		// then - it must not be to save a new WrokingTime document
		verify(timeRecordingRepository, never()).save(workingTime);
	}

	// update WorkingTimeSlotFor
	@DisplayName("JUnit test for successfully Updating an existing WorkingTime ")
	@Test
	public void givenAWorkerAprojectAndAworkingTimeSlot_whenUpdatingExistingDocument_thenReturnDocumentObject()
			throws IOException {

		// given -
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(workingTime);
		// given -
		BDDMockito.given(aWorkingDay.containsThis("", workingTime.getWrokingDays())).willReturn(WorkingDay);
		// when - updating a new document
		ResponseEntity<WorkingTime> savedOne = timeRecordingServices.updateTimeSlotFor(workingTime.getCoWorkerName(),
				workingTime.getProjectName(), workingTimeSlot);

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
					breakTimeSlot);
		});

		// then - it must not be to save a new WrokingTime document
		verify(timeRecordingRepository, never()).save(workingTime);
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
		BDDMockito.given(aWorkingDay.containsThis("", workingTime.getWrokingDays())).willReturn(WorkingDay);
		// when - updating a new document
		ResponseEntity<WorkingTime> savedOne = timeRecordingServices
				.updateBreakTimeSlotFor(workingTime.getCoWorkerName(), workingTime.getProjectName(), breakTimeSlot);

		// then - it must return HttpStatus.CREATED
		assertEquals(HttpStatus.OK, savedOne.getStatusCode());

	}
}
