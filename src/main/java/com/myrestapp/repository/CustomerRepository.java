package com.myrestapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.web.bind.annotation.PostMapping;

import com.myrestapp.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	@Query("select c from Customer c join c.product p where p.id=?1")
	List<Customer> getCustomerByProductId(Long pid);
}
