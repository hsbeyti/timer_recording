package hsbeyti.time.recording.entities;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Field;

public class BreakTimeSlot {

	private String description;
	private String duration;
	
	//private String duration;
    
	public BreakTimeSlot() {
		
	}

	
	public BreakTimeSlot(String description, String duration) {
		super();
		this.description = description;
		this.duration = duration;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	




	/*@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (!(obj instanceof BreakTimeSlot))
			return false;
		BreakTimeSlot other = (BreakTimeSlot) obj;
		return Objects.equals(other.description, this.description)
				&& Objects.equals(other.durationsInMinutes, this.durationsInMinutes);
	}

	@Override
	public String toString() {

		return "Break{" + "description=" + this.description + ", durations='" + this.durationsInMinutes + '}';
	}*/

}
