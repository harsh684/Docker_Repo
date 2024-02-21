package com.hexaware.customerrestapi.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.customerrestapi.dto.CustomerDTO;
import com.hexaware.customerrestapi.dto.CustomerProductVO;
import com.hexaware.customerrestapi.dto.Product;
import com.hexaware.customerrestapi.entity.Customer;
import com.hexaware.customerrestapi.service.ICustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

	@Autowired
	ICustomerService service;
	
	@PostMapping("/add")
	public Customer	addCustomer(@RequestBody CustomerDTO customerDTO) {
		return service.addCustomer(customerDTO);
	}
	
	@GetMapping("/get/{customerId}")
	public CustomerDTO	getCustomerById(@PathVariable int customerId) {
		return service.getCustomerById(customerId);
	}
	
	@GetMapping("/getall")
	public List<Customer> getAllCustomer(){
		return service.getAllCustomer();
	}

	@GetMapping("/customer-product/{customerId}")
	public CustomerProductVO getCustomerAndProductById(@PathVariable int customerId) {
		return service.getCustomerAndProductById(customerId);
	}
	
	@DeleteMapping("/delete/customer-product/{customerId}")
	public String deleteCustomerAndProduct(@PathVariable int customerId) {
		return service.deleteCustomerAndProduct(customerId);
	}
	
	@PutMapping("/update/customer-product")
	public CustomerProductVO updateCustomerAndProduct(@RequestBody CustomerProductVO cpvo){
		return service.updateCustomerAndProduct(cpvo.getCustomer(), cpvo.getProduct());
	}
}
