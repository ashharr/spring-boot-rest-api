package com.myrestapp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.myrestapp.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	List<Review> findAllByProductId(Long pid);

	List<Review> findAllByCustomerId(Long cid);

	@Modifying
	@Transactional
	@Query("delete from Review r where r.product.id=?1")
	void deleteProduct(Long productId);

	// delete from review r where r.product_id=?

	@Modifying
	@Transactional
	@Query("delete from Review r where r.customer.id=?1")
	void deleteCustomer(Long customerId);
	
	@Modifying
	@Transactional
	@Query("select r from Review r join r.product p where p.price > ?1")
	List<Review> getReviewByProductPrice(Double price);

	@Query("select r from Review r join r.product p join r.customer c where p.price > ?1 and c.city=?2")
	List<Review> getReviewByProductPriceAndCustomerCity(Double price, String city);
}
