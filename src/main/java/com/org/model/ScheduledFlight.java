package com.org.model;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ScheduledFlight {

	@Id
	@Column(name = "schedule_flight_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long scheduleFlightId;

	@Column(name = "available_seats")
	@NotNull
	private long availableSeats;


	@OneToOne(fetch = FetchType.EAGER)
	private Airport srcAirport;

	@OneToOne(fetch = FetchType.EAGER)
	private Airport dstnAirport;

	@Column(name = "departure_date")
//	@JsonFormat(pattern = "mm-dd-yyyy HH:mm:ss")
	private String deptDateTime;

	@Column(name = "arrival_date")
//	@JsonFormat(pattern = "mm-dd-yyyy HH:mm:ss")
	private String arrDateTime;

	public long getScheduleFlightId() {
		return scheduleFlightId;
	}

	public void setScheduleFlightId(long scheduleFlightId) {
		this.scheduleFlightId = scheduleFlightId;
	}

	public long getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(long availableSeats) {
		this.availableSeats = availableSeats;
	}

	public Airport getSrcAirport() {
		return srcAirport;
	}

	public void setSrcAirport(Airport srcAirport) {
		this.srcAirport = srcAirport;
	}

	public Airport getDstnAirport() {
		return dstnAirport;
	}

	public void setDstnAirport(Airport dstnAirport) {
		this.dstnAirport = dstnAirport;
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

	public ScheduledFlight(long scheduleFlightId,  long availableSeats, Airport srcAirport, Airport dstnAirport,
			String deptDateTime, String arrDateTime) {
		super();
		this.scheduleFlightId = scheduleFlightId;
		this.availableSeats = availableSeats;
		this.srcAirport = srcAirport;
		this.dstnAirport = dstnAirport;
		this.deptDateTime = deptDateTime;
		this.arrDateTime = arrDateTime;
	}

	public ScheduledFlight() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(arrDateTime, availableSeats, deptDateTime, dstnAirport, scheduleFlightId, srcAirport);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduledFlight other = (ScheduledFlight) obj;
		return Objects.equals(arrDateTime, other.arrDateTime) && availableSeats == other.availableSeats
				&& Objects.equals(deptDateTime, other.deptDateTime) && Objects.equals(dstnAirport, other.dstnAirport)
				&& scheduleFlightId == other.scheduleFlightId && Objects.equals(srcAirport, other.srcAirport);
	}


	

}
