package hsbeyti.time.recording.timerecording;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;



@SpringBootApplication
@EnableMongoRepositories("hsbeyti.time.recording.repository")
@ComponentScan("hsbeyti.time.recording.*")
@PropertySource("classpath:app.properties")
public class TimerecordingApplication {
	
	// static LocalTime tesTime= LocalTime.now();
	public static void main(String[] args) {

		SpringApplication.run(TimerecordingApplication.class, args);

	}

}
