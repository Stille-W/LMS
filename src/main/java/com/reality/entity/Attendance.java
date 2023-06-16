package com.reality.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name = "attendance")
public class Attendance {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Date date;
	
	@Column 
	private String division;
	
	@Column(name="workhours")
	private String workHours;
	
	@Column
	private String project;
	
	@Column
	private String place;
	
	@Column
	private String remarks;
	
	@Column
	private String startTime;
	
	@Column
	private String endTime;
	
	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Attendance(Integer id, Date date, String division, String workHours, String project, String place,
			String remarks, String startTime, String endTime, User user) {
		super();
		this.id = id;
		this.date = date;
		this.division = division;
		this.workHours = workHours;
		this.project = project;
		this.place = place;
		this.remarks = remarks;
		this.startTime = startTime;
		this.endTime = endTime;
		this.user = user;
	}



	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@ManyToOne
	@JoinColumn(name = "uid", referencedColumnName = "id")
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getWorkHours() {
		return workHours;
	}

	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
