package com.GreenFlow.v1.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GreenFlow.v1.application.port.in.UserUseCase;
import com.GreenFlow.v1.application.port.out.PasswordEncoderPort;
import com.GreenFlow.v1.application.port.out.UserPersistencePort;
import com.GreenFlow.v1.domain.exception.UserNotFoundException;
import com.GreenFlow.v1.domain.model.User;

@Service
@Transactional(readOnly = true)
public class UserApplicationService implements UserUseCase {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    public UserApplicationService(
            UserPersistencePort userPersistencePort,
            PasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return getExistingUser(id);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        User userToPersist = User.builder()
                .username(user.getUsername())
                .password(passwordEncoderPort.encode(user.getPassword()))
                .role(user.getRole())
                .build();

        return userPersistencePort.save(userToPersist);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {
        User existingUser = getExistingUser(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setRole(user.getRole());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoderPort.encode(user.getPassword()));
        }

        return userPersistencePort.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User existingUser = getExistingUser(id);
        userPersistencePort.delete(existingUser);
    }

    private User getExistingUser(Long id) {
        return userPersistencePort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}