package hsbeyti.time.recording.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import hsbeyti.time.recording.entities.WorkingTime;
import hsbeyti.time.recording.timerecording.TimerecordingApplication;


@SpringBootTest(classes = TimerecordingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimeRecordingRepositoryTest {
	@Autowired
	private TimeRecordingRepository timeRecordingRepository;
	
	//private WorkingTime workingTime;
	
@BeforeEach
public void setup(){
 String worker="Houssein";
 String peoject="gitterstar";
}

	@Test
	void givenWorkerandProject_whenSearchedFor_thenRetrunWorkDocument() {
		// give a worker name and a project name
		String worker="test123";
		String peoject="ww Starp";
		
		// when searched for return complete Document
		Optional<WorkingTime> workingTime=timeRecordingRepository.findByCoWorkerNameAndProjectName(worker, peoject);
		
		// then the returned Object is not null
		assertTrue(workingTime.isPresent());
		
	
		
	}
	@Test // not found
	void givenWorkerandProject_whenSearchedFor_thenRetrunNull() {
		// give a worker name and a project name
		String worker="Houssein";
		String peoject="gitterstar";
		
		// when seacrhed for return complete Document
		Optional<WorkingTime> workingTime=timeRecordingRepository.findByCoWorkerNameAndProjectName(worker, peoject);
		
		// then the returned Object is  null
		assertFalse(workingTime.isPresent());
	
		
	}

}
