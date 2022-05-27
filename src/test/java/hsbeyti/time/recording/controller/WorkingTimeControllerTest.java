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
import hsbeyti.time.recording.repository.TimeRecordingRepository;
import hsbeyti.time.recording.services.TimeRecordingServices;

@ExtendWith(MockitoExtension.class)
class WorkingTimeControllerTest {

	private MockMvc mvc;

	@Mock
	private TimeRecordingServices timeRecordingServices;;

	@InjectMocks
	private WorkingTimeController workingTimeController;

	private WorkingTime workingTime;
	private WorkingTimeSlot workingTimeSlot;
	private WorkingDay WorkingDay;
	private BreakTimeSlot breakTimeSlot;
	// This object will be magically initialized by the initFields method below.
	private JacksonTester<WorkingTime> jsonWorkingTime;

	public void initialize() {

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

	@BeforeEach
	public void setup() {
		// create all objects needed
		initialize();
		JacksonTester.initFields(this, new ObjectMapper());
		// MockMvc standalone approach
		mvc = MockMvcBuilders.standaloneSetup(workingTimeController)
				.setControllerAdvice(new WorkingTimeControllerExceptionHandler())
				.addFilters(new WorkingTimeControllerFilter()).build();

	}

	@Test
	public void canCreateANewWorkingTimeo() throws Exception {
		// when
		MockHttpServletResponse response = mvc.perform(post("/api/v1/timerecording/workingtime/")
				.contentType(MediaType.APPLICATION_JSON).content(jsonWorkingTime.write(workingTime).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}

}
