package hsbeyti.time.recording.time.date.utility;


import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter; 


public class TodayDateCreator implements DateFormater {
	public final String DAY_FORMAT = "dd.MM.yyyy";
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
	public String getTodayDate(LocalDateTime todayDate) {
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(DAY_FORMAT);
		return todayDate.format(myFormatObj);
	
	}
    
	
	
}
