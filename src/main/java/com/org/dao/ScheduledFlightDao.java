package com.org.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.org.model.ScheduledFlight;

@Repository
public interface ScheduledFlightDao extends JpaRepository<ScheduledFlight, Long>{
	
}
