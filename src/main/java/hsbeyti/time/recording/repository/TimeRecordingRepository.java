package hsbeyti.time.recording.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import hsbeyti.time.recording.entities.*;

@Repository
public interface TimeRecordingRepository extends MongoRepository<WorkingTime, String>{
	WorkingTime findByCoWorkerNameAndProjectName (String wrokerName, String projectName);
}
