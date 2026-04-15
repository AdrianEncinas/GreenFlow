package com.GreenFlow.v1.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.GreenFlow.v1.dto.UserDTO;
import com.GreenFlow.v1.exception.NotFoundException;
import com.GreenFlow.v1.mapper.Mapper;
import com.GreenFlow.v1.model.User;
import com.GreenFlow.v1.respository.UserRepository;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(Mapper::toDTO)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        return Mapper.toDTO(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found")));
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = Mapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return Mapper.toDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setRole(userDTO.getRole());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(existingUser);
        return Mapper.toDTO(existingUser);
    }

    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(existingUser);
    }

}
