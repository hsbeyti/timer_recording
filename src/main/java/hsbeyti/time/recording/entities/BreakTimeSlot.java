package hsbeyti.time.recording.entities;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Field;

public class BreakTimeSlot {

	private String description;
	
	private Integer durationsInMinutes;
	
	


	public BreakTimeSlot(String description, Integer durationsInMinutes) {
	
		this.description = description;
		this.durationsInMinutes = durationsInMinutes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDurations() {
		return durationsInMinutes;
	}

	public void setDurations(Integer durations) {
		this.durationsInMinutes = durations;
	}

	@Override
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
	}

}
