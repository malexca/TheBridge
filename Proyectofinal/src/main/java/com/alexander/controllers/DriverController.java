package com.alexander.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.models.Driver;
import com.alexander.repositories.DriverRepository;

@RestController
@RequestMapping("/api")
public class DriverController {
	
	@Autowired
	DriverRepository driverRepository;
	
	@GetMapping("/driver")
	public ResponseEntity<List<Driver>> getAll(@RequestParam(name="text", required = false) String name) {
		List<Driver> res = new ArrayList<>();
		if (name == null) {
			driverRepository.findAll().forEach(res::add);
		} else {
			driverRepository.findByFirstNameContaining(name).forEach(res::add);
		}
		if (res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/driver/{id}")
	public ResponseEntity<Driver> getDriver(@PathVariable("id") int id) {
		Driver driver = driverRepository.findById(id).orElse(null);
		if (driver == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(driver, HttpStatus.OK);
		}
	}
	
	@PostMapping("/driver")
	public ResponseEntity<Driver> addDriver(@RequestBody Driver driver) {
		Driver temp = driverRepository.save(new Driver(driver.getFirstName(), driver.getLastName()));
		return new ResponseEntity<>(temp, HttpStatus.CREATED);
	}
	
	@PutMapping("/driver/{id}")
	public ResponseEntity<Driver> updateDriver(@PathVariable("id") int id, @RequestBody Driver driver) {
		Driver temp = driverRepository.findById(id).orElse(null);
		if (temp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			temp.setDriverId(driver.getDriverId());
			return new ResponseEntity<>(driverRepository.save(temp), HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/driver/{id}")
	public ResponseEntity<HttpStatus> deleteDriver(@PathVariable("id") int id) {
		driverRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
