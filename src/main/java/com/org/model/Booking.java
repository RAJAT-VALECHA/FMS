package com.org.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long bookingId;
	
	@JsonIgnore
	private String bookingDate;
	
	private String srcAirport;
	private String dstnAirport;
	private int noOfPassengers;

	@Column(name = "departure_date")
//	@JsonFormat(pattern = "mm-dd-yyyy HH:mm:ss")
	private String deptDateTime;

	@Column(name = "arrival_date")
//	@JsonFormat(pattern = "mm-dd-yyyy HH:mm:ss")
	private String arrDateTime;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="scheduleFlightId")
	 private ScheduledFlight scheduledFlight;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="userId")
	 private User user;
	

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDeptDateTime() {
		return deptDateTime;
	}

	public void setDeptDateTime(String deptDateTime) {
		this.deptDateTime = deptDateTime;
	}

	public String getArrDateTime() {
		return arrDateTime;
	}

	public void setArrDateTime(String arrDateTime) {
		this.arrDateTime = arrDateTime;
	}

	

	public Booking(long bookingId, String bookingDate, String srcAirport, String dstnAirport, int noOfPassengers,
			String deptDateTime, String arrDateTime) {
		super();
		this.bookingId = bookingId;
		this.bookingDate = bookingDate;
		this.srcAirport = srcAirport;
		this.dstnAirport = dstnAirport;
		this.noOfPassengers = noOfPassengers;
		this.deptDateTime = deptDateTime;
		this.arrDateTime = arrDateTime;

	}

	public ScheduledFlight getScheduledFlight() {
		return scheduledFlight;
	}

	public void setScheduledFlight(ScheduledFlight scheduledFlight) {
		this.scheduledFlight = scheduledFlight;
	}

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public int getNoOfPassengers() {
		return noOfPassengers;
	}

	public void setNoOfPassengers(int noOfPassengers) {
		this.noOfPassengers = noOfPassengers;
	}

	public String getSrcAirport() {
		return srcAirport;
	}

	public void setSrcAirport(String srcAirport) {
		this.srcAirport = srcAirport;
	}

	public String getDstnAirport() {
		return dstnAirport;
	}

	public void setDstnAirport(String dstnAirport) {
		this.dstnAirport = dstnAirport;
	}
}
