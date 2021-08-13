package com.myrestapp.controller;

import java.util.List;

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
	public Review postReview(@RequestBody Review review, @PathVariable("pid") Long productId,
			@PathVariable("cid") Long customerId) {
		Product product = productRepository.getById(productId);
		Customer customer = customerRepository.getById(customerId);
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
	@DeleteMapping("/review/product/{pid}")
	public void deleteReviewByProduct(@PathVariable("pid") Long productId) {
		reviewRepository.deleteProduct(productId);
	}

	@DeleteMapping("/review/customer/{cid}")
	public void deleteReviewByCustomer(@PathVariable("cid") Long customerId) {
		reviewRepository.deleteCustomer(customerId);
	}

	// PUT
	@PutMapping
	public void updateReview() {

	}
	// EXTRAS FOR FUNCTIONALITY

	@GetMapping("/review/product/{price}")
	public List<Review> getReviewByProductPrice(@PathVariable Double price) {
		return reviewRepository.getReviewByProductPrice(price);
	}

	@GetMapping("/review/product/customer/{price}/{city}")
	public List<Review> getReviewByProductPriceandCustomerCity(@PathVariable("price") Double price,
			@PathVariable("city") String city) {
		return reviewRepository.getReviewByProductPriceAndCustomerCity(price, city);
	}

}
