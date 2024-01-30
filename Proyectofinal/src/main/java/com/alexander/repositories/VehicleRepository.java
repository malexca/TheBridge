package com.alexander.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.models.Vehicle;

import jakarta.transaction.Transactional;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
	
	List <Vehicle> findByTypeContaining (String type);
	List <Vehicle> findByDriverDriverId(int id);
	
	@Transactional
	void deleteByDriverDriverId(int id);

}
