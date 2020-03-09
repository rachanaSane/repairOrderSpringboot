package com.auto.repairorder.view;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RepairOrderDto {

	private Long id;

	@NotEmpty(message = "Please provide repairer name.")
	private String repairerName;

	@Future(message = "startDateTime must be a future date.")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDateTime;

	@NotEmpty(message = "Please provide duration in format HH:MM:SS")
	@JsonFormat(pattern = "HH:MM:SS")
	private String duration;

	private String orderStatusDescription;

	public RepairOrderDto() {

	}

	public RepairOrderDto(Long id, @NotEmpty(message = "Please provide farm name.") String repairerName,
			@Future(message = "startDateTime must be a future date.") LocalDateTime startDateTime,
			@NotEmpty(message = "Please provide duration in format HH:MM:SS") String duration,
			String orderStatusDescription) {
		super();
		this.id = id;
		this.repairerName = repairerName;
		this.startDateTime = startDateTime;
		this.duration = duration;
		this.orderStatusDescription = orderStatusDescription;
	}

	
	public String getRepairerName() {
		return repairerName;
	}

	public void setRepairerName(String repairerName) {
		this.repairerName = repairerName;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getOrderStatusDescription() {
		return orderStatusDescription;
	}

	public void setOrderStatusDescription(String orderStatusDescription) {
		this.orderStatusDescription = orderStatusDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RepairOrderDto [id=" + id + ", repairerName=" + repairerName + ", startDateTime=" + startDateTime
				+ ", duration=" + duration + ", orderStatusDescription=" + orderStatusDescription + "]";
	}

	
}
