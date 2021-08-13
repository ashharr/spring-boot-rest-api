package com.myrestapp.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myrestapp.dto.ProductDto;
import com.myrestapp.model.Product;
import com.myrestapp.model.Vendor;
import com.myrestapp.repository.ProductRepository;
import com.myrestapp.repository.VendorRepository;

@RestController
public class ProductController {
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@PostMapping("/product/{id}")
	public Product postProduct(@RequestBody Product product, @PathVariable("id") Long vendorId) {
		Vendor vendor = vendorRepository.getById(vendorId);
		product.setVendor(vendor); 
		return productRepository.save(product);
	}
	
	@GetMapping("/product")
	public List<ProductDto> getAllProducts(@RequestParam(name = "sort", required = false, defaultValue = "ASC") String direction){
		List<Product> list = new ArrayList<>();
		if(direction.equalsIgnoreCase("DESC")) {
			list = productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
		}
		list = productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
		
		List<ProductDto> listDto = new ArrayList<>();
	
		for(Product p : list) {
			ProductDto dto = new ProductDto();
			dto.setId(p.getId());
			dto.setName(p.getVendor().getName());
			dto.setPrice(p.getPrice());
			dto.setTitle(p.getTitle());
			listDto.add(dto);
		}
		return listDto;
		
	}
	
	@GetMapping("/product/vendor/{vid}")
	public List<Product> getAllProductsByVendor(@PathVariable("vid") Long vid){
		return productRepository.findByVendorId(vid);
	}
	
	@GetMapping("/product/{pid}")
	public Product getSingleProduct(@PathVariable("pid") Long pid) {
		return productRepository.getById(pid);
	}
	
	@PutMapping("product/{pid}")
	public Product updateProduct(@PathVariable("pid") Long pid, @RequestBody Product newProduct) {
		Product product = productRepository.getById(pid);
		product.setTitle(newProduct.getTitle());
		product.setPrice(newProduct.getPrice());
		product.setVendor(newProduct.getVendor());
		return productRepository.save(product);
	}
	
	@DeleteMapping("/product/{pid}")
	public void deleteProduct(@PathVariable("pid") Long pid) {
		productRepository.deleteById(pid);
	}
	
	@GetMapping("product/customer/{cid}")
	public List<Product> getProductByCustomerId(@PathVariable("cid")Long cid) {
		return productRepository.getProductByCustomerId(cid);
	}
}
