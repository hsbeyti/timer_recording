package hsbeyti.time.recording.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.apache.catalina.core.ApplicationContext;
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
import hsbeyti.time.recording.timerecording.TimerecordingApplication;

@SpringBootTest(classes = TimerecordingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimeRecordingRepositoryTest {
	@Autowired
	private TimeRecordingRepository timeRecordingRepository;

	@Value("${spring.application.name}")
	private String testProp;

	@BeforeEach
	public void setup() {
		String worker = "Houssein";
		String peoject = "gitterstar";
		System.out.print(testProp);
	}

	@DisplayName("Retrieve a document for a given working working on aproject")
	@Test
	void givenWorkerandProject_whenSearchedFor_thenRetrunWorkDocument() {
		// give a worker name and a project name
		String worker = "test123";
		String peoject = "ww Starp";

		// when searched for return complete Document
		WorkingTime workingTime = timeRecordingRepository.findByCoWorkerNameAndProjectName(worker, peoject);
		System.out.print(workingTime);
		// then the returned Object is not null
		assertNotNull(workingTime);

	}

	@Test // not found
	void givenWorkerandProject_whenSearchedFor_thenRetrunNull() {
		// give a worker name and a project name
		String worker = "Houssein";
		String peoject = "gitterstar";

		// when searched for return complete Document
		WorkingTime workingTime = timeRecordingRepository.findByCoWorkerNameAndProjectName(worker, peoject);
		System.out.print(workingTime);
		// then the returned Object is not null
		assertNull(workingTime);

	}

}
