package com.GreenFlow.v1.application.port.in;

import java.util.List;

import com.GreenFlow.v1.domain.model.User;

public interface UserUseCase {

    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}