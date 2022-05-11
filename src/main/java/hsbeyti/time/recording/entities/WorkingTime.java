package hsbeyti.time.recording.entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
// Document used to store co-worker working time on a specific project
@Document(collection = "working_time")
public class WorkingTime {
	@Id
	private String id;
	@Field("worker_name")
	private String coWorkerName;
	@Field("project_name")
	private String projectName;
	@Field("project_order_unmber")
	private String projectOrderNumber;
	@Field("wroking_days")
	List<WorkingDay> wrokingDays;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCoWorkerName() {
		return coWorkerName;
	}

	public void setCoWorkerName(String coWorkerName) {
		this.coWorkerName = coWorkerName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectOrderNumber() {
		return projectOrderNumber;
	}

	public void setProjectOrderNumber(String projectOrderNumber) {
		this.projectOrderNumber = projectOrderNumber;
	}

	public List<WorkingDay> getWrokingDays() {
		return wrokingDays;
	}

	public void setWrokingDays(List<WorkingDay> wrokingDays) {
		this.wrokingDays = wrokingDays;
	}

}
