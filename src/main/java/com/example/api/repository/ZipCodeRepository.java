package com.example.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.api.domain.ZipCode;

@Repository
public interface ZipCodeRepository extends CrudRepository<ZipCode, Long> {
	

}