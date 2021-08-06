package com.myrestapp.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myrestapp.model.Customer;
import com.myrestapp.model.Product;
import com.myrestapp.model.Review;
import com.myrestapp.repository.CustomerRepository;
import com.myrestapp.repository.ProductRepository;
import com.myrestapp.repository.ReviewRepository;

@RestController
public class ReviewController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	// POST
	@PostMapping("/review/{pid}/{cid}")
	public Review postReview(@RequestBody Review review, @PathVariable("pid") Long productId, @PathVariable("cid") Long customerId) {
		Product product = productRepository.getById(productId);
		Customer customer=customerRepository.getById(customerId);
		review.setCustomer(customer);
		review.setProduct(product);
		return reviewRepository.save(review);
	}

	// GETALL
	@GetMapping("/review/customer")
	public List<Review> getAllReviewsByCustomer(@RequestParam("cid") Long cid) {
		return reviewRepository.findAllByCustomerId(cid);
	}
	
	@GetMapping("/review/product")
	public List<Review> getAllReviewsByProduct(@RequestParam("pid") Long pid) {
		return reviewRepository.findAllByProductId(pid);
	}

	// GETONE
	@GetMapping
	public void getOneReview() {
		
	}

	// DELETE
	@DeleteMapping("/review/product")
	@Transactional
	public void deleteReviewByProduct(@RequestParam("pid") Long pid) {
		reviewRepository.deleteReviewByProductId(pid);
	}
	
	@DeleteMapping("/review/customer")
	@Transactional
	public void deleteReviewByCustomer(@RequestParam("cid") Long cid) {
		reviewRepository.deleteReviewByCustomerId(cid);
	}
	// PUT
	@PutMapping
	public void updateReview() {

	}
	// EXTRAS FOR FUNCTIONALITY
}
