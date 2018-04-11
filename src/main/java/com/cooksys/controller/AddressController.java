package com.cooksys.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.dto.AddressDto;
import com.cooksys.dto.UserDto;
import com.cooksys.service.AddressService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("address")
public class AddressController {

	private AddressService addressService;

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	@GetMapping
	@ApiOperation(value = "", nickname = "getAllAddresses")
	public List<AddressDto> getAll(@RequestParam(required = false) String city,
			@RequestParam(required = false) String state) {
		if (city == null & state == null) {
			return addressService.getAll();
		} else if (city == null & state != null) {
			return addressService.findByState(state);
		} else {
			return addressService.findByCity(city);
		}
	}

	@GetMapping("{id}")
	@ApiOperation(value = "", nickname = "getAddressById")
	public AddressDto getAddressById(@PathVariable Long id) {
		return addressService.getAddressById(id);
	}

	@GetMapping("{id}/residents")
	@ApiOperation(value = "", nickname = "getResidentsByAddress")
	public List<UserDto> getResidentsByAddress(@PathVariable Long id) {
		return addressService.getResidentsByAddressId(id);
	}

	@PostMapping
	@ApiOperation(value = "", nickname = "postNewAddress")
	public Long post(@RequestBody AddressDto addressDto, HttpServletResponse httpResponse) {
		Long id = addressService.post(addressDto);
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return id;
	}

	@PutMapping("{id}")
	@ApiOperation(value = "", nickname = "putAddressWithId")
	public void put(@PathVariable Long id, @RequestBody AddressDto addressDto, HttpServletResponse httpResponse) {
		addressService.put(id, addressDto);
	}

	@DeleteMapping("{id}")
	@ApiOperation(value = "", nickname = "deleteAddressAtId")
	public void delete(@PathVariable Long id, HttpServletResponse httpResponse) {
		addressService.delete(id);
	}
}