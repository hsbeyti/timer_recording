package hsbeyti.time.recording.time.date.utility;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class TodayDateCreatorTest {

	private LocalDateTime todayDate = LocalDateTime.now();

	private DateFormater testingDayDate = new TodayDateCreator(todayDate);

	@Test
	public void should_returnTodayDate_whenApplied() {
		// given
		DateTimeFormatter myFormatedDay = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		String actualTodayDate = todayDate.format(myFormatedDay);
		// when
		String toTestTodayDate = testingDayDate.getTodayDate(todayDate);
		// System.out.println(toTestTodaDate);
		// then
		assertEquals(toTestTodayDate, actualTodayDate);
	}
}
