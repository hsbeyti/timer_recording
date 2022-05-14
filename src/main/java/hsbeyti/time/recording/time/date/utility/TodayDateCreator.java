package hsbeyti.time.recording.time.date.utility;


import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component; 


public class TodayDateCreator implements DateFormater {
	//@Value("${time.recording.DAY_FORMAT}")
	private  String DAY_FORMAT="dd.MM.yyyy";
	private LocalDateTime todayDate;
	
	

	public TodayDateCreator(LocalDateTime todayDate) {
	
		this.todayDate = todayDate;
	}


	public LocalDateTime getTodayDate() {
		return todayDate;
	}


	public void setTodayDate(LocalDateTime todayDate) {
		this.todayDate = todayDate;
	}


	@Override
	public String getTodayDateString() {
		DateTimeFormatter myFormatedDay = DateTimeFormatter.ofPattern(DAY_FORMAT);
		return  todayDate.format(myFormatedDay);
		
	}



    
	
	
}
