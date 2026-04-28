package com.GreenFlow.v1.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.GreenFlow.v1.application.port.out.PasswordEncoderPort;
import com.GreenFlow.v1.application.port.out.UserPersistencePort;
import com.GreenFlow.v1.application.service.UserApplicationService;
import com.GreenFlow.v1.domain.exception.UserNotFoundException;
import com.GreenFlow.v1.domain.model.Role;
import com.GreenFlow.v1.domain.model.User;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UserApplicationService userService;

    @Test
    void createUser_shouldSaveUserAndReturnDto() {
        User user = User.builder()
                .username("Test1")
                .password("Password")
                .role(Role.ROLE_USER)
                .build();
        when(passwordEncoderPort.encode("Password")).thenReturn("encoded-password");
        when(userPersistencePort.save(any(User.class))).thenAnswer(invocation -> {
            User persistedUser = invocation.getArgument(0);
            persistedUser.setId(1L);
            return persistedUser;
        });

        User result = userService.createUser(user);

        assertEquals(1L, result.getId());
        assertEquals("Test1", result.getUsername());
        assertEquals("encoded-password", result.getPassword());
        assertEquals(Role.ROLE_USER, result.getRole());
        verify(passwordEncoderPort).encode("Password");
        verify(userPersistencePort).save(any(User.class));
    }

    @Test
    void getUserById_whenUserExists_shouldReturnDto() {
        User user = new User(1L, "Test1", "Password", Role.ROLE_USER);
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Test1", result.getUsername());
        assertEquals(Role.ROLE_USER, result.getRole());
    }

    @Test
    void getUserById_whenUserNotFound_shouldThrowNotFoundException() {
        when(userPersistencePort.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));

        assertEquals("User not found with id: 1", exception.getMessage());
    }
}
