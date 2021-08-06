package com.myrestapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myrestapp.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	List<Review> findAllByProductId(Long pid);
	
	List<Review> findAllByCustomerId(Long cid);
	
	void deleteReviewByProductId(Long pid);
	
	void deleteReviewByCustomerId(Long cid);
}
