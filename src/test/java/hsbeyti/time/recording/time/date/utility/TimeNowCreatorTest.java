package hsbeyti.time.recording.time.date.utility;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class TimeNowCreatorTest {

	private LocalDateTime timeNow = LocalDateTime.now();

	private TimeFormater testingTimeNow = new TimeNowCreator(timeNow);

	@Test
	public void hould_returnTimeNow_whenApplied() {
		// given
		DateTimeFormatter myFormatedTime = DateTimeFormatter.ofPattern("HH:mm:ss");
		String actualTimeNow = timeNow.format(myFormatedTime);
		// when
		String toTestTimeNow = testingTimeNow.getTimeNow(timeNow);
		// System.out.println(toTestTimeNow);
		// then
		assertEquals(actualTimeNow, toTestTimeNow);
	}
}
