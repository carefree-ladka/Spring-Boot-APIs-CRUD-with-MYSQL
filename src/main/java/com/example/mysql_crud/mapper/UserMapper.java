package com.example.mysql_crud.mapper;

import com.example.mysql_crud.dto.UserDTO;
import com.example.mysql_crud.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
     UserDTO toDto(User user);
     User toEntity(UserDTO dto);
}
