package hsbeyti.time.recording.initilization;

import hsbeyti.time.recording.entities.*;


public class WorkingTimeInitializationTemplate {
	 private WorkingTime workingTime;
		private WorkingTimeSlot workingTimeSlot;
		private WorkingDay workingDay;
		private BreakTimeSlot breakTimeSlot;
		
		
		public WorkingTimeInitializationTemplate() {
		
		}
		public WorkingTimeInitializationTemplate(WorkingTime workingTime, WorkingTimeSlot workingTimeSlot,
				WorkingDay workingDay, BreakTimeSlot breakTimeSlot) {
			
			this.workingTime = workingTime;
			this.workingTimeSlot = workingTimeSlot;
			this.workingDay = workingDay;
			this.breakTimeSlot = breakTimeSlot;
		}
		
		public WorkingTime getWorkingTime() {
			return workingTime;
		}
		public void setWorkingTime(WorkingTime workingTime) {
			this.workingTime = workingTime;
		}
		public WorkingTimeSlot getWorkingTimeSlot() {
			return workingTimeSlot;
		}
		public void setWorkingTimeSlot(WorkingTimeSlot workingTimeSlot) {
			this.workingTimeSlot = workingTimeSlot;
		}
		public WorkingDay getWorkingDay() {
			return this.workingDay;
		}
		public void setWorkingDay(WorkingDay workingDay) {
			this.workingDay = workingDay;
		}
		public BreakTimeSlot getBreakTimeSlot() {
			return breakTimeSlot;
		}
		public void setBreakTimeSlot(BreakTimeSlot breakTimeSlot) {
			this.breakTimeSlot = breakTimeSlot;
		}
		

}
