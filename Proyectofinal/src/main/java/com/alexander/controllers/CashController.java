package com.alexander.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.models.Cash;
import com.alexander.repositories.CashRepository;

@RestController
@RequestMapping("/api")
public class CashController {
	
	@Autowired
	CashRepository cashRepository;
	
	@GetMapping("/cash") 
	public ResponseEntity<List<Cash>> getAll(@RequestParam(required = false) Date date) {
		List<Cash> res = new ArrayList<>();
		if (date == null) {
			cashRepository.findAll().forEach(res::add);
		} else {
			cashRepository.findByDateContaining(date).forEach(res::add);
		}
		if (res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
