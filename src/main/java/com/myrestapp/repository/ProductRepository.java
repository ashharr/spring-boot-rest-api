package com.myrestapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.web.bind.annotation.PostMapping;

import com.myrestapp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	List<Product> findByVendorId(Long id);
	
	Product findByTitle(String title);
	
	List<Product> findByPrice(Double price);
}
