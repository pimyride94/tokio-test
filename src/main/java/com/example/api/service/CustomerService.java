package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.api.domain.Customer;
import com.example.api.domain.ZipCode;
import com.example.api.repository.CustomerRepository;
import com.example.api.repository.ZipCodeRepository;
import com.example.handler.ErrorHandler;

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
		if(customerExistsByEmail(customer))
			return new ResponseEntity<>("Email already registered", HttpStatus.BAD_REQUEST);
		
		if(!customer.getZipCode().isEmpty())
			validateAndCreateCustomerReferenceToZipCode(customer);

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
		
		if(!exists)	
			return new ResponseEntity<>("Customer with id "+id+" not found", HttpStatus.NOT_FOUND);
			
		customer = updateCustomerFieldsAndReferences(id, customer);
		repository.save(customer);
		return new ResponseEntity<>("Customer with id "+id+" updated with success", HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<String> deleteCustomer(Long id) {
		boolean exists = repository.existsById(id);
		
		if(!exists)
			return new ResponseEntity<>("Customer with id "+id+" not found", HttpStatus.NOT_FOUND);
		
		Customer customer = findById(id).get();
		repository.delete(customer);
		return new ResponseEntity<>("Customer with id "+id+" deleted with success", HttpStatus.OK);
	}
	
	private boolean customerExistsByEmail(Customer customer) {
		List<Customer> list = findAll();
		return list.stream().anyMatch(item -> customer.getEmail().equals(item.getEmail()));
	}
	
	private void populateZipCodeDataFromViaCepWebService(ZipCode zipCode) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new ErrorHandler());
		ZipCode response = restTemplate.getForObject("https://viacep.com.br/ws/"+zipCode.getCep()+"/json/", ZipCode.class);
		BeanUtils.copyProperties(response, zipCode);
	}
	
	private void validateAndCreateCustomerReferenceToZipCode(Customer customer) {
		for(ZipCode item: customer.getZipCode()) {
			populateZipCodeDataFromViaCepWebService(item);
			item.setZipCustomer(customer);
		}
	}
	
	
	private Customer updateCustomerFieldsAndReferences(Long id, Customer customer) {
		Customer updatedCustomer = findById(id).get();
		BeanUtils.copyProperties(customer, updatedCustomer, "id", "zipCode");
		return updatedCustomer;
	}

}
