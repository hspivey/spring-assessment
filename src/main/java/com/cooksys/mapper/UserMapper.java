package com.cooksys.mapper;

import org.mapstruct.Mapper;

import com.cooksys.dto.UserDto;
import com.cooksys.entity.User;

@Mapper(componentModel = "spring", uses = { ReferenceMapper.class })
public interface UserMapper {

	UserDto toDto(User entity);

	User toEntity(UserDto dto);
}
