package com.myrestapp.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.myrestapp.model.Customer;
import com.myrestapp.repository.CustomerRepository;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@PostMapping("/customer")
	private void postCustomer(@RequestBody Customer customer) {
		customerRepository.save(customer);
	}
	
	
	@GetMapping("/customer")
	private List<Customer> getAllCustomers() {
		List<Customer> list = customerRepository.findAll();
		return list;
	}
	
	@GetMapping("/customer/{id}") 	// path parameter
	private Customer getCustomerbyId(@PathVariable("id") Long id) {
		// to stop serialization of param error add in app.prop
		// spring.jackson.serialization.fail-on-empty-beans=false
		return customerRepository.getById(id);
	}
	
	@PutMapping("/customer/{id}")
	private Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer newCustomerVal) {
		// we send the updated fields in the body in JSON format
		Customer customerDB = customerRepository.getById(id);
		customerDB.setName(newCustomerVal.getName());
		customerDB.setCity(newCustomerVal.getCity());
		return customerRepository.save(customerDB);
	}
	
	@DeleteMapping("customer/{id}")
	private void deleteCustomer(@PathVariable("id") Long id) {
		customerRepository.deleteById(id);
	}
}
