package com.GreenFlow.v1.application.port.out;

import java.util.List;
import java.util.Optional;

import com.GreenFlow.v1.domain.model.User;

public interface UserPersistencePort {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    User save(User user);

    void delete(User user);
}