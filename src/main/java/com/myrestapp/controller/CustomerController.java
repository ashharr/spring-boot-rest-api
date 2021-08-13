package com.myrestapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.myrestapp.model.Customer;
import com.myrestapp.model.Product;
import com.myrestapp.repository.CustomerRepository;
import com.myrestapp.repository.ProductRepository;



@RestController
@CrossOrigin(origins = "http://localhost:8686", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductRepository productRepository;

	@PostMapping("/customer")
	private void postCustomer(@RequestBody Customer customer) {
		customerRepository.save(customer);
	}

	@GetMapping("/customer")
	private List<Customer> getAllCustomers(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10000") Integer size) {
		// activate paging on this
		Pageable pageable = PageRequest.of(page, size);
		List<Customer> list = customerRepository.findAll(pageable).getContent();
		return list;
	}

	@GetMapping("/customer/{id}") // path parameter
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

	@PostMapping("/customer/product/{cid}/{pid}")
	private void purchaseAPI(@PathVariable("cid") Long cid, @PathVariable("pid") Long pid) {
		// to insert into relationship table we need to insert owner relation
		Product product = productRepository.getById(pid);
		Customer customer = customerRepository.getById(cid);
		List<Product> list = new ArrayList<>();
		list.add(product);
		customer.setProduct(list);
		customerRepository.save(customer);
	}

	@PostMapping("/customer/multiple/product/{cid}")
	private void purchaseMultiple(@PathVariable("cid") Long cid, @RequestParam("strId") String strId) {
		// to insert into relationship table we need to insert owner relation
		Customer customer = customerRepository.getById(cid);

		String[] strArry = strId.split(","); // [1,2,3,4,5]
		List<Long> listID = new ArrayList<>();
		for (String s : strArry) {
			listID.add(Long.parseLong(s));
		}
		List<Product> listProduct = productRepository.findAllById(listID);
		customer.setProduct(listProduct);
		customerRepository.save(customer);
	}

	@DeleteMapping("/customer/product/{cid}/{pid}")
	private void deletePurchase(@PathVariable("cid") Long cid, @PathVariable("pid") Long pid) {
		// deletes specific entry not all the products bought by customer
		Customer customer = customerRepository.getById(cid);
		List<Product> productList = customer.getProduct(); // 9,10
		List<Product> productListMain = new ArrayList<>();
		for (Product p : productList) {
			if (!p.getId().equals(pid)) // keeping the all products except the one we wanna delete
				productListMain.add(p);
		}
		customer.setProduct(productListMain);
		customerRepository.save(customer);
	}

	@GetMapping("customer/product/{pid}")
	public List<Customer> getCustomerByProductId(@PathVariable("pid") Long pid) {
		return customerRepository.getCustomerByProductId(pid);
	}

}
