package com.myrestapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myrestapp.model.Vendor;
import com.myrestapp.repository.VendorRepository;

@RestController
public class VendorController {
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@PostMapping("/vendor")
	public Vendor postVendor(@RequestBody Vendor vendor) {
		return vendorRepository.save(vendor);
	}
	
	@GetMapping("/vendor")
	public List<Vendor> getAllVendors(){
		return vendorRepository.findAll();
	}
	
	@GetMapping("/vendor/{id}")
	public Vendor getById(@PathVariable("id") Long id){
		return vendorRepository.getById(id);
	}
	
	@PutMapping("/vendor/{id}")
	public Vendor updateVendor(@RequestBody Vendor newVendor, @PathVariable("id") Long id){
		Vendor vendorDB = vendorRepository.getById(id);
		vendorDB.setName(newVendor.getName());
		vendorDB.setCity(newVendor.getCity());
		vendorDB.setRating(newVendor.getRating());
		return vendorRepository.save(vendorDB);
	}
	
	@DeleteMapping("/vendor/{id}")
	public void  deleteById(@PathVariable("id") Long id){
		vendorRepository.deleteById(id);
	}
	
	
}
