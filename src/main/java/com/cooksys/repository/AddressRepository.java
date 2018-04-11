package com.cooksys.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findByState(String state);

	List<Address> findByCity(String city);

}
