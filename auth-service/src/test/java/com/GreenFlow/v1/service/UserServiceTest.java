package com.GreenFlow.v1.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
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
    void createUser_shouldEncodePasswordAndSave() {
        User user = User.builder()
                .username("Test1")
                .password("Password")
                .role(Role.ROLE_USER)
                .build();
        when(passwordEncoderPort.encode("Password")).thenReturn("encoded-password");
        when(userPersistencePort.save(any(User.class))).thenAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
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
    void getAllUsers_shouldReturnAllUsers() {
        List<User> users = List.of(
                new User(1L, "Test1", "pass1", Role.ROLE_USER),
                new User(2L, "Test2", "pass2", Role.ROLE_ADMIN));
        when(userPersistencePort.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Test1", result.get(0).getUsername());
        assertEquals("Test2", result.get(1).getUsername());
        verify(userPersistencePort).findAll();
    }

    @Test
    void getUserById_whenUserExists_shouldReturnUser() {
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

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(1L));

        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void updateUser_whenUserExists_shouldUpdateAndReturn() {
        User existing = new User(1L, "OldName", "old-encoded", Role.ROLE_USER);
        User updates = User.builder()
                .username("NewName")
                .password("NewPassword")
                .role(Role.ROLE_ADMIN)
                .build();
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(passwordEncoderPort.encode("NewPassword")).thenReturn("new-encoded");
        when(userPersistencePort.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.updateUser(1L, updates);

        assertEquals("NewName", result.getUsername());
        assertEquals("new-encoded", result.getPassword());
        assertEquals(Role.ROLE_ADMIN, result.getRole());
        verify(passwordEncoderPort).encode("NewPassword");
        verify(userPersistencePort).save(existing);
    }

    @Test
    void updateUser_whenPasswordBlankOrNull_shouldNotReEncode() {
        User existing = new User(1L, "OldName", "old-encoded", Role.ROLE_USER);
        User updates = User.builder()
                .username("NewName")
                .password(null)
                .role(Role.ROLE_ADMIN)
                .build();
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(userPersistencePort.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.updateUser(1L, updates);

        assertEquals("NewName", result.getUsername());
        assertEquals("old-encoded", result.getPassword());
        verify(passwordEncoderPort, never()).encode(any());
    }

    @Test
    void updateUser_whenUserNotFound_shouldThrowNotFoundException() {
        when(userPersistencePort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(99L, User.builder().username("x").build()));
    }

    @Test
    void deleteUser_whenUserExists_shouldDelete() {
        User existing = new User(1L, "Test1", "pass", Role.ROLE_USER);
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(existing));

        userService.deleteUser(1L);

        verify(userPersistencePort).delete(existing);
    }

    @Test
    void deleteUser_whenUserNotFound_shouldThrowNotFoundException() {
        when(userPersistencePort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userPersistencePort, never()).delete(any());
    }
}
