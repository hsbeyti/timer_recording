package hsbeyti.time.recording.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import hsbeyti.time.recording.entities.WorkingTime;
import hsbeyti.time.recording.initilization.WorkingTimeInitialisationInterface;
import hsbeyti.time.recording.initilization.WorkingTimeInitializationImpl;
import hsbeyti.time.recording.timerecording.TimerecordingApplication;

@SpringBootTest(classes = TimerecordingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimeRecordingRepositoryTest {
	@Autowired
	private TimeRecordingRepository timeRecordingRepository;

	@Value("${spring.application.name}")
	private String testProp;
	private String worker = "Houssein";
	private String peoject = "gitterstar";
	private WorkingTime workingTime;
	private WorkingTimeInitialisationInterface workingTimeInitializationImpl;

	@BeforeEach
	public void setup() {
		timeRecordingRepository.deleteAll();
		workingTimeInitializationImpl = new WorkingTimeInitializationImpl();
		workingTime = workingTimeInitializationImpl.createAWorkingTimeObject(worker, peoject);

		timeRecordingRepository.save(workingTime);
	}

	

	@DisplayName("Retrieve a document for a given working working on aproject")
	@Test
	void givenWorkerandProject_whenSearchedFor_thenRetrunWorkDocument() {

		// when searched for
		WorkingTime workingTime = timeRecordingRepository.findByCoWorkerNameAndProjectName(worker, peoject);
		System.out.print(workingTime);
		// then the returned Object is not null
		assertNotNull(workingTime);

	}

	@Test // not found
	void givenWorkerandProject_whenSearchedFor_thenRetrunNull() {

		// when searched for
		WorkingTime workingTime = timeRecordingRepository.findByCoWorkerNameAndProjectName("AnyNameNotThere", peoject);

		// then the returned Object is null
		assertNull(workingTime);

	}

	@DisplayName("Retrieve a document for a given working ")
	@Test
	void givenWorker_whenSearchedFor_thenRetrunWorkDocument() {

		// when searched for
		List<WorkingTime> workingTime = timeRecordingRepository.findByCoWorkerName(worker);
		System.out.print(workingTime.get(0));
		// then the returned Object is not null
		assertNotNull(workingTime.get(0));

	}

	@DisplayName("Fail to Retrieve a document for a given worker ")
	@Test
	void givenWorker_whenSearchedFor_thenRetrunNull() {

		// when searched for a Document
		List<WorkingTime> workingTime = timeRecordingRepository.findByCoWorkerName("AnyNameNotThere");

		// then the returned Document is empty
		assertTrue(workingTime.isEmpty());

	}
}
