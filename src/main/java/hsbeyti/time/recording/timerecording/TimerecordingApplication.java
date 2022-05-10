package hsbeyti.time.recording.timerecording;

import java.time.LocalTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimerecordingApplication {
	//static LocalTime tesTime= LocalTime.now();
	public static void main(String[] args) {
		//System.out.println(tesTime);
		SpringApplication.run(TimerecordingApplication.class, args);
		
	}

}
