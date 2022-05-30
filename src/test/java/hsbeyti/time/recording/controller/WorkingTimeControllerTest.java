package hsbeyti.time.recording.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import hsbeyti.time.recording.entities.*;
import hsbeyti.time.recording.exceptions.NoExistingWorkTimeException;
import hsbeyti.time.recording.initilization.WorkingTimeInitialisationInterface;
import hsbeyti.time.recording.initilization.WorkingTimeInitializationImpl;
import hsbeyti.time.recording.services.TimeRecordingServices;

@ExtendWith(MockitoExtension.class)
class WorkingTimeControllerTest {

	private MockMvc mvc;

	@Mock
	private TimeRecordingServices timeRecordingServices;

	@InjectMocks
	private WorkingTimeController workingTimeController;

	private WorkingTime workingTime;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<WorkingTime> jsonWorkingTime;

	private WorkingTimeInitialisationInterface workingTimeInitializationImpl;

	@BeforeEach
	public void setup() {
		// create all objects needed
		 workingTimeInitializationImpl= new WorkingTimeInitializationImpl ();
		workingTime =workingTimeInitializationImpl.createAWorkingTimeObject("testWorker","testProject","12.02.2022");
		JacksonTester.initFields(this, new ObjectMapper());
		// MockMvc standalone approach
		mvc = MockMvcBuilders.standaloneSetup(workingTimeController)
				.setControllerAdvice(new WorkingTimeControllerExceptionHandler())
				.addFilters(new WorkingTimeControllerFilter()).build();

	}
	@Test
	public void canCreateANewWorkingTime() throws Exception {

		// when
		MockHttpServletResponse response = mvc.perform(post("/api/v1/timerecording/workingtime/")
				.contentType(MediaType.APPLICATION_JSON).content(jsonWorkingTime.write(workingTime).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	public void canRetrieveByWrokerAndProjectNamesWhenExists() throws Exception {
		// given

		given(timeRecordingServices.getWorkingTimeFor("testWroker", "testProject"))
		.willReturn(workingTime);
		// when
		MockHttpServletResponse response = mvc.perform(
				get("/api/v1/timerecording/workingtime/testWroker/testProject").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		// WorkingTime workingTimetw=initialize();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		 assertThat(response.getContentAsString()).isEqualTo(
		 jsonWorkingTime.write(workingTime).getJson()
		 );
	}
	
	@Test
    public void canRetrieveByWrokerAndProjectNamesWhenDoesNotExist() throws Exception {
        // given
        given(timeRecordingServices.getWorkingTimeFor("testWroker1", "testProject"))
                .willThrow(new NoExistingWorkTimeException());

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/v1/timerecording/workingtime/testWroker1/testProject")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }
	
}
