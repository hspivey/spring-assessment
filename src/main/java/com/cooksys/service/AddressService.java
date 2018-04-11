package com.cooksys.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.dto.AddressDto;
import com.cooksys.dto.UserDto;
import com.cooksys.entity.User;
import com.cooksys.mapper.AddressMapper;
import com.cooksys.mapper.UserMapper;
import com.cooksys.repository.AddressRepository;

@Service
public class AddressService {

	private AddressRepository repo;
	private AddressMapper mapper;
	private UserMapper userMapper;

	public AddressService(AddressRepository repo, AddressMapper mapper, UserMapper userMapper) {
		this.repo = repo;
		this.mapper = mapper;
		this.userMapper = userMapper;
	}

	public List<AddressDto> getAll() {
		return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	// public boolean has(Long id) {
	// return repo.existsById(id);
	// }
	//
	// private void mustExist(Long id) {
	// if (!has(id))
	// throw new ReferencedEntityNotFoundException(User.class, id);
	// }

	public AddressDto getAddressById(Long id) {
		// mustExist(id);
		return mapper.toDto(repo.findById(id).get());
	}

	public List<UserDto> getResidentsByAddressId(Long id) {
		return repo.findById(id).get().getResidents().stream().map(userMapper::toDto).collect(Collectors.toList());
	}

	public Long post(AddressDto addressDto) {
		addressDto.setId(null);
		return repo.save(mapper.toEntity(addressDto)).getId();
	}

	public void put(Long id, AddressDto addressDto) {
		// mustExist(id);
		addressDto.setId(id);
		repo.save(mapper.toEntity(addressDto));
	}

	@Transactional
	public void delete(Long id) {
		// mustExist(id);
		for (User resident : repo.findById(id).get().getResidents()) {
			resident.setAddress(null);
		}
		repo.deleteById(id);
	}

	public List<AddressDto> findByState(String state) {
		return repo.findByState(state).stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public List<AddressDto> findByCity(String city) {
		return repo.findByCity(city).stream().map(mapper::toDto).collect(Collectors.toList());
	}
}
