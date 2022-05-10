package hsbeyti.time.recording.timerecording;

import java.time.LocalTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("hsbeyti.time.*")
@EnableMongoRepositories("hsbeyti.time.recording.repository")
public class TimerecordingApplication {
	//static LocalTime tesTime= LocalTime.now();
	public static void main(String[] args) {
		//System.out.println(tesTime);
		SpringApplication.run(TimerecordingApplication.class, args);
		
	}

}
