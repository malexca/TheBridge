package com.alexander.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.models.Cash;

import jakarta.transaction.Transactional;

public interface CashRepository extends JpaRepository<Cash, Integer> {
	
	List <Cash> findByDateContaining (Date date);
	List <Cash> findByVehicleVehicleId(int id);
	
	@Transactional
	void deleteByVehicleVehicleId(int id);

}
