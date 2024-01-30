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
import com.alexander.models.Vehicle;
import com.alexander.repositories.DriverRepository;
import com.alexander.repositories.VehicleRepository;

@RestController
@RequestMapping("/api")
public class VehicleController {
	
	@Autowired
	VehicleRepository vehicleRepository;
	@Autowired
	DriverRepository driverRepository;
	
	@GetMapping("/vehicle")
	public ResponseEntity<List<Vehicle>> getAll(@RequestParam(name="text", required = false) String type) {
		List<Vehicle> res = new ArrayList<>();
		if (type == null) {
			vehicleRepository.findAll().forEach(res::add);
		} else {
			vehicleRepository.findByTypeContaining(type).forEach(res::add);
		}
		if (res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/vehicle/{id}")
	public ResponseEntity<Vehicle> getVehicle(@PathVariable("id") int id) {
		Vehicle vehicle = vehicleRepository.findById(id).orElse(null);
		if (vehicle == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(vehicle, HttpStatus.OK);
		}
	}
	
	@GetMapping("/driver/{id}/vehicle")
	public ResponseEntity<List<Vehicle>> getAllByDriver(@PathVariable("id") int id) {
		List<Vehicle> res = new ArrayList<>();
		vehicleRepository.findByDriverDriverId(id).forEach(res::add);
		if (res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping("/driver/{id}/vehicle")
	public ResponseEntity<Vehicle> addVehicle(@PathVariable("id") int id, @RequestBody Vehicle vehicle) {
		Driver driver = driverRepository.findById(id).orElse(null);
		if (driver == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Vehicle temp = new Vehicle(vehicle.getType(), driver);
		return new ResponseEntity<>(vehicleRepository.save(temp), HttpStatus.CREATED);
	}
	
	@PostMapping("/vehicle")
	public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
		Vehicle temp = vehicleRepository.save(new Vehicle(vehicle.getType(), vehicle.getDriver()));
		return new ResponseEntity<>(temp, HttpStatus.CREATED);
	}
	
	@PutMapping("/vehicle/{id}")
	public ResponseEntity<Vehicle> updateVehicle(@PathVariable("id") int id, @RequestBody Vehicle vehicle) {
		Vehicle temp = vehicleRepository.findById(id).orElse(null);
		if (temp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			temp.setType(vehicle.getType());
			return new ResponseEntity<>(vehicleRepository.save(temp), HttpStatus.OK);
		}
	}

	@DeleteMapping("/driver/{id}/vehicle")
	public ResponseEntity<HttpStatus> deleteVehicleDriver(@PathVariable("id") int id) {
		if (!driverRepository.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			vehicleRepository.deleteByDriverDriverId(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/vehicle/{id}")
	public ResponseEntity<HttpStatus> deleteVehicle(@PathVariable("id") int id) {
		vehicleRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
