package com.GreenFlow.v1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.GreenFlow.v1.dto.UserDTO;
import com.GreenFlow.v1.exception.NotFoundException;
import com.GreenFlow.v1.mapper.Mapper;
import com.GreenFlow.v1.model.User;
import com.GreenFlow.v1.respository.UserRepository;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(Mapper::toDTO)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        return Mapper.toDTO(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found")));
    }

    public UserDTO createUser(User user) {
        userRepository.save(user);
        return Mapper.toDTO(user);
    }

    public UserDTO updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        userRepository.save(existingUser);
        return Mapper.toDTO(existingUser);
    }

    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(existingUser);
    }

}
