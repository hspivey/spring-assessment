package com.cooksys.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.dto.AddressDto;
import com.cooksys.dto.UserDto;
import com.cooksys.entity.Address;
import com.cooksys.entity.User;
import com.cooksys.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("user")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	@ApiOperation(value = "", nickname = "getAllUsers")
	public List<UserDto> getAll() {
		return userService.getAll();
	}

	@GetMapping("{id}")
	@ApiOperation(value = "", nickname = "getUserById")
	public UserDto get(@PathVariable Long id) {
		return userService.get(id);
	}

	@GetMapping("{id}/address")
	@ApiOperation(value = "", nickname = "getAddressByUser")
	public AddressDto getAddressByUser(@PathVariable Long id) {
		return userService.getAddressByUser(id);
	}

	@GetMapping("{id}/relations")
	@ApiOperation(value = "", nickname = "getRelationsByUser")
	public List<UserDto> getRelationsByUser(@PathVariable Long id) {
		return userService.getRelationsByUser(id);
	}

	@PostMapping
	@ApiOperation(value = "", nickname = "postNewUser")
	public Long post(@RequestBody UserDto userDto, HttpServletResponse httpResponse) {
		Long id = userService.post(userDto);
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return id;
	}

	@PutMapping("{id}")
	@ApiOperation(value = "", nickname = "putUserWithId")
	public void put(@PathVariable Long id, @RequestBody UserDto userDto, HttpServletResponse httpResponse) {
		userService.put(id, userDto);
	}

	@DeleteMapping("{id}")
	@ApiOperation(value = "", nickname = "deleteUserAtId")
	public void delete(@PathVariable Long id, HttpServletResponse httpResponse) {
		userService.delete(id);
	}

	@PostMapping("{id}/relations/{relationId}")
	@Transactional
	public void attachRelation(@PathVariable(name = "id") User user, @PathVariable(name = "relationId") User us) {
		user.getRelations().add(us);
		us.getRelations().add(user);
	}

	@PostMapping("{id}/address/{addressId}")
	@Transactional
	public void attachManager(@PathVariable(name = "id") User user, @PathVariable(name = "addressId") Address ad) {
		user.setAddress(ad);
		ad.getResidents().add(user);
	}
}
