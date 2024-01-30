package com.alexander.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.models.Driver;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
	
	List <Driver> findByFirstNameContaining (String name);

}
