package hsbeyti.time.recording.services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import hsbeyti.time.recording.entities.*;
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

		

	}

	@DisplayName("JUnit test for save WorkingTime method")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() throws IOException {
      
		// given -
		workingTime= new WorkingTime();
		workingTime.setCoWorkerName("testWroker");
		workingTime.setProjectName("testProject");
		workingTime.setProjectOrderNumber("testOrder");
		workingTime.setWrokingDays(new ArrayList<WorkingDay>());
		// when -  action or the behaviour that we are going test
		ResponseEntity<WorkingTime> savedOne=timeRecordingServices.createTimeRecording(workingTime);
		
		
		// then - verify the output
        assertNotNull(savedOne);
	}

}
