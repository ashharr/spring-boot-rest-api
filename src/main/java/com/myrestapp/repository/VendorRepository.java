package com.myrestapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myrestapp.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long>{

}
