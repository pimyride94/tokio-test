package com.example.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.api.domain.Customer;
import com.example.api.domain.ZipCode;

@Repository
public interface ZipCodeRepository extends CrudRepository<ZipCode, Long> {
	

}