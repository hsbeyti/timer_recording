package hsbeyti.time.recording.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


import hsbeyti.time.recording.entities.*;

@Repository
public interface TimeRecordingRepository extends MongoRepository<WorkingTime, String>{
     WorkingTime findByCoWorkerNameAndProjectName (String wrokerName, String projectName);
	List<WorkingTime> findByCoWorkerName (String wrokerName);
}
