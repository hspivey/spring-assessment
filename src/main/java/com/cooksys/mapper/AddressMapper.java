package com.cooksys.mapper;

import org.mapstruct.Mapper;

import com.cooksys.dto.AddressDto;
import com.cooksys.entity.Address;

@Mapper(componentModel = "spring", uses = { ReferenceMapper.class })
public interface AddressMapper {

	AddressDto toDto(Address entity);

	Address toEntity(AddressDto dto);
}
