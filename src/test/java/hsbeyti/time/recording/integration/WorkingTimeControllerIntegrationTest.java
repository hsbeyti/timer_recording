package hsbeyti.time.recording.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

import hsbeyti.time.recording.entities.*;
import hsbeyti.time.recording.initilization.WorkingTimeInitialisationInterface;
import hsbeyti.time.recording.initilization.WorkingTimeInitializationImpl;
import hsbeyti.time.recording.repository.TimeRecordingRepository;
import hsbeyti.time.recording.timerecording.TimerecordingApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TimerecordingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WorkingTimeControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TimeRecordingRepository timeRecordingRepository;

	@Autowired
	private ObjectMapper objectMapper;
	private WorkingTime workingTime;
	

	

	@BeforeEach
	void setup() {
		// create all objects needed
		
		WorkingTimeInitialisationInterface workingTimeInitializationImpl= new WorkingTimeInitializationImpl ();
		workingTime =workingTimeInitializationImpl.createAWorkingTimeObject("testWorker","testProject","12.02.2022");

		timeRecordingRepository.deleteAll();
	}

	@Test
	public void givenworkingTimeObject_whenCreateworkingTime_thenReturnSavedworkingTime() throws Exception {

		// given - precondition or setup
		// DOne by @BeforeEach

		// when - action or behaviour that we are going test
		ResultActions response = mockMvc.perform(post("/api/v1/timerecording/workingtime/")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(workingTime)));

		// then - verify the result or output using assert statements
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.coWorkerName", is(workingTime.getCoWorkerName())))
				.andExpect(jsonPath("$.projectName", is(workingTime.getProjectName())));

	}

}
