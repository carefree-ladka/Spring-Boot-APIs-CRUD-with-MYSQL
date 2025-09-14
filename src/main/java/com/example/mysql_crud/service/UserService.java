package com.example.mysql_crud.service;

import com.example.mysql_crud.repository.UserRepository;
import com.example.mysql_crud.dto.UserDTO;
import com.example.mysql_crud.entity.User;
import com.example.mysql_crud.exception.ResourceNotFoundException;
import com.example.mysql_crud.mapper.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.mysql_crud.constants.ErrorConstants.USER_NOT_FOUND;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                       .stream()
                       .map(userMapper::toDto)
                       .toList();
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                       .map(userMapper::toDto)
                       .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO createUser(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toDto(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO updateUser(Long id, UserDTO dto) {
        User existing = userRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
        ;
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setAge(dto.getAge());
        return userMapper.toDto(userRepository.save(existing));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        userRepository.delete(user);
    }
}
