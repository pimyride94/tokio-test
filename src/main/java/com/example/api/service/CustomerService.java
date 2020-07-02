package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.domain.Customer;
import com.example.api.domain.ZipCode;
import com.example.api.repository.CustomerRepository;
import com.example.api.repository.ZipCodeRepository;

@Service
public class CustomerService {

	private CustomerRepository repository;
	
	@Autowired
	private ZipCodeRepository zipRep;
	
	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}
	
	public Iterable<ZipCode> findAllZipCodes() {
		return zipRep.findAll();
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public ResponseEntity<String> saveCustomer(Customer customer) {
		if(!customer.getZipCode().isEmpty())
			createZipCodeReferenceToCustomer(customer);

		repository.save(customer);
		return new ResponseEntity<>("Customer created with success", HttpStatus.CREATED);
	}
	
	@Transactional
	public Iterable<Customer> saveAllCustomers(List<Customer> customerList) {
		return repository.saveAll(customerList);
	}
	
	@Transactional 
	public ResponseEntity<String> updateCustomer(Long id, Customer customer) {
		boolean exists = repository.existsById(id);
		
		if(!exists) {	
			return new ResponseEntity<>("Customer with id "+id+" not found", HttpStatus.NOT_FOUND);
			
		} else {
			customer.setId(id);
			repository.save(customer);
			return new ResponseEntity<>("Customer with id "+id+" updated with success", HttpStatus.OK);
		}
	}
	
	private void createZipCodeReferenceToCustomer(Customer customer) {
		for(ZipCode item: customer.getZipCode()) {
			item.setCustomer(customer);
		}
	}

}
