package com.hexaware.customerrestapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hexaware.customerrestapi.dto.CustomerDTO;
import com.hexaware.customerrestapi.dto.CustomerProductVO;
import com.hexaware.customerrestapi.dto.Product;
import com.hexaware.customerrestapi.entity.Customer;
import com.hexaware.customerrestapi.repository.CustomerRepository;

@Service
public class CustomerServiceImp implements ICustomerService {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CustomerRepository repo;
	
	@Override
	public Customer addCustomer(CustomerDTO customerDTO) {
		// TODO Auto-generated method stub
		Customer c = new Customer();
		c.setCustomerId(customerDTO.getCustomerId());
		c.setCustomerName(customerDTO.getCustomerName());
		c.setDateOfBirth(customerDTO.getDateOfBirth());
		c.setProductId(customerDTO.getProductId());
		
		return repo.save(c);
	}

	@Override
	public CustomerDTO getCustomerById(int customerId) {
		
		CustomerDTO cdto = new CustomerDTO();
		Customer c = repo.findById(customerId).orElse(null);
		cdto.setCustomerId(c.getCustomerId());
		cdto.setCustomerName(c.getCustomerName());
		cdto.setDateOfBirth(c.getDateOfBirth());
		cdto.setProductId(c.getProductId());
		
		return cdto;
	}

	@Override
	public List<Customer> getAllCustomer() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public CustomerProductVO getCustomerAndProductById(int customerId) {


		//step 1
		CustomerDTO cdto = getCustomerById(customerId);
		
		//step 2 call rest api from product microservice .getProductById()
		//use restTemplate which will et us product object
		ResponseEntity<Product> res = restTemplate.getForEntity("http://localhost:8282/api/products/get/"+cdto.getProductId(), Product.class);
		
		Product p = res.getBody();
		
		CustomerProductVO cpvo = new CustomerProductVO(cdto,p);
		
		return cpvo;
	}

	@Override
	public String deleteCustomerAndProduct(int customerId) {
		// TODO Auto-generated method stub	
		Customer c = repo.findById(customerId).orElse(null);
		restTemplate.delete("http://localhost:8282/api/products/delete/"+c.getProductId());
		
		repo.delete(repo.findById(customerId).orElse(null));
		
		return "Customer and associated product deleted";
	}

	@Override
	public CustomerProductVO updateCustomerAndProduct(CustomerDTO customer, Product product) {
		// TODO Auto-generated method stub
		Customer c = new Customer();
		c.setCustomerId(customer.getCustomerId());
		c.setCustomerName(customer.getCustomerName());
		c.setDateOfBirth(customer.getDateOfBirth());
		c.setProductId(customer.getProductId());
		
		repo.save(c);
		restTemplate.put("http://localhost:8282/api/products/update", product);
		
		CustomerProductVO cpvo = new CustomerProductVO();
		cpvo.setCustomer(customer);
		cpvo.setProduct(product);
		
		return cpvo;
	}

}
