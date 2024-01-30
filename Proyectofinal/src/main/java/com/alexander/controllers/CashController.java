package com.alexander.controllers;

import java.util.ArrayList;
import java.util.Date;
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

import com.alexander.models.Cash;
import com.alexander.models.Vehicle;
import com.alexander.repositories.CashRepository;
import com.alexander.repositories.VehicleRepository;

@RestController
@RequestMapping("/api")
public class CashController {
	
	@Autowired
	CashRepository cashRepository;
	@Autowired
	VehicleRepository vehicleRepository;
	@GetMapping("/cash") 
	
	public ResponseEntity<List<Cash>> getAll(@RequestParam(required = false) Date date) {
		List<Cash> res = new ArrayList<>();
		if (date == null) {
			cashRepository.findAll().forEach(res::add);
		} else {
			cashRepository.findByDate(date).forEach(res::add);
		}
		if (res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/cash/{id}")
	public ResponseEntity<Cash> getCash(@PathVariable("id") int id) {
		Cash cash = cashRepository.findById(id).orElse(null);
		if (cash == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(cash, HttpStatus.OK);
		}
	}
	
	@GetMapping("/vehicle/{id}/cash")
	public ResponseEntity<List<Cash>> getAllByVehicle(@PathVariable("id") int id) {
		List<Cash> res = new ArrayList<>();
		cashRepository.findByVehicleVehicleId(id).forEach(res::add);
		if (res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("/vehicle/{id}/cash")
	public ResponseEntity<Cash> addCash(@PathVariable("id") int id, @RequestBody Cash cash) {
		Vehicle vehicle = vehicleRepository.findById(id).orElse(null);
		if (vehicle == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Cash temp = new Cash(cash.getDate(), cash.getKilometre(), cash.getIncome(), cash.getExpense(), vehicle);
		return new ResponseEntity<>(cashRepository.save(temp), HttpStatus.CREATED);
	}
	
	@PostMapping("/cash")
	public ResponseEntity<Cash> addCash(@RequestBody Cash cash) {
		Cash temp = cashRepository.save(new Cash(cash.getDate(), cash.getKilometre(), cash.getIncome(), 
				cash.getExpense(), cash.getVehicle()));
		return new ResponseEntity<>(temp, HttpStatus.CREATED);
	}
	
	@PutMapping("/cash/{id}")
	public ResponseEntity<Cash> updateCash(@PathVariable("id") int id, @RequestBody Cash cash) {
		Cash temp = cashRepository.findById(id).orElse(null);
		if (temp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			temp.setDate(cash.getDate());
			return new ResponseEntity<>(cashRepository.save(temp), HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/vehicle/{id}/cash")
	public ResponseEntity<HttpStatus> deleteCashVehicle(@PathVariable("id") int id) {
		if (!vehicleRepository.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			cashRepository.deleteByVehicleVehicleId(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/cash/{id}")
	public ResponseEntity<HttpStatus> deleteCash(@PathVariable("id") int id) {
		cashRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	

}
