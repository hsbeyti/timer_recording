package hsbeyti.time.recording.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import hsbeyti.time.recording.exceptions.UserAllreadExistException;
import hsbeyti.time.recording.repository.TimeRecordingRepository;
import hsbeyti.time.recording.timerecording.TimerecordingApplication;

@SpringBootTest(classes = TimerecordingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class TimeRecordingServicesTest {
	
	@Mock
	TimeRecordingRepository timeRecordingRepository;

	@InjectMocks
	private TimeRecordingServices timeRecordingServices;

	
	private WorkingTime workingTime;
	@BeforeEach
	
	public void setup() {

		workingTime= new WorkingTime();
		workingTime.setCoWorkerName("testWroker");
		workingTime.setProjectName("testProject");
		workingTime.setProjectOrderNumber("testOrder");
		workingTime.setWrokingDays(new ArrayList<WorkingDay>());

	}

	@DisplayName("JUnit test for save WorkingTime method")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() throws IOException {
      
		// given -
	 
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(null);
		BDDMockito.given(timeRecordingRepository.save(workingTime)).willReturn(workingTime);
		// when -  creating a new document
		ResponseEntity<WorkingTime> savedOne=timeRecordingServices.createTimeRecording(workingTime);
		
		
		// then - it must be not null
        assertEquals(HttpStatus.CREATED,savedOne.getStatusCode());
	}
	
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnBadRequest() throws IOException {
      
		// given -
	 
		BDDMockito.given(timeRecordingRepository.findByCoWorkerNameAndProjectName(workingTime.getCoWorkerName(),
				workingTime.getProjectName())).willReturn(workingTime);
		//BDDMockito.given(timeRecordingRepository.save(workingTime)).willReturn(workingTime);
		// when -  creating a new document
		 assertThrows(UserAllreadExistException.class, ()-> {
			 timeRecordingServices.createTimeRecording(workingTime);
			 });
		
		
		// then - it must be not null
       verify(timeRecordingRepository,never()).save(workingTime);
	}
	

}
