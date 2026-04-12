package com.GreenFlow.v1.service;

import java.util.List;

import com.GreenFlow.v1.dto.UserDTO;
import com.GreenFlow.v1.model.User;

public interface IUserService {

    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(User user);
    UserDTO updateUser(Long id, User user);
    void deleteUser(Long id);
    
}
