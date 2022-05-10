package hsbeyti.time.recording.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BreakTimeSlotTest {

	
	@Test
	public void equalsaBreak() {
		//given  & when
		BreakTimeSlot testBreak = new BreakTimeSlot();
		testBreak.setDescription("get");
		testBreak.setDurations(20);
		BreakTimeSlot test2Break = new BreakTimeSlot();
		test2Break.setDescription("get");
		test2Break.setDurations(20);
		//then
		assertEquals(test2Break, testBreak);
	}

	

	@Test
	public void failDurationaBreak() {
		//given  & when
		BreakTimeSlot testBreak = new BreakTimeSlot();
		testBreak.setDescription("get");
		testBreak.setDurations(20);
		BreakTimeSlot test2Break = new BreakTimeSlot();
		test2Break.setDescription("get");
		test2Break.setDurations(21);
		//then
		assertNotEquals(test2Break, testBreak);
	}


}
