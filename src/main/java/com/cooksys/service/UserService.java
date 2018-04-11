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
import com.cooksys.repository.UserRepository;

@Service
public class UserService {

	private UserRepository repo;
	private UserMapper mapper;
	private AddressMapper addressMapper;

	public UserService(UserRepository repo, UserMapper mapper, AddressMapper addressMapper) {
		this.repo = repo;
		this.mapper = mapper;
		this.addressMapper = addressMapper;
	}

	public List<UserDto> getAll() {
		return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	// public boolean has(Long id) {
	// return repo.existsById(id);
	// }

	// private void mustExist(Long id) {
	// if(!has(id))
	// throw new ReferencedEntityNotFoundException(User.class, id);
	// }

	public UserDto get(Long id) {
		// mustExist(id);
		return mapper.toDto(repo.findById(id).get());
	}

	public AddressDto getAddressByUser(Long id) {

		return addressMapper.toDto(repo.findById(id).get().getAddress());
	}

	public Long post(UserDto userDto) {
		userDto.setId(null);
		return repo.save(mapper.toEntity(userDto)).getId();
	}

	public List<UserDto> getRelationsByUser(Long id) {
		return repo.findById(id).get().getRelations().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public void put(Long id, UserDto userDto) {
		// mustExist(id);
		userDto.setId(id);
		repo.save(mapper.toEntity(userDto));
	}

	@Transactional
	public void delete(Long id) {
		// mustExist(id);
		for (User relation : repo.findById(id).get().getRelations()) {
			relation.getRelations().remove(repo.findById(id).get());
		}
		repo.deleteById(id);
	}
}
