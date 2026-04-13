package com.GreenFlow.v1.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.GreenFlow.v1.dto.UserDTO;
import com.GreenFlow.v1.exception.NotFoundException;
import com.GreenFlow.v1.model.Role;
import com.GreenFlow.v1.model.User;
import com.GreenFlow.v1.respository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldSaveUserAndReturnDto() {
        UserDTO dto = UserDTO.builder()
                .username("Test1")
                .password("Password")
                .role(Role.ROLE_USER)
                .build();
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDTO result = userService.createUser(dto);

        assertEquals("Test1", result.getUsername());
        assertEquals("Password", result.getPassword());
        assertEquals(Role.ROLE_USER, result.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getUserById_whenUserExists_shouldReturnDto() {
        User user = new User(1L, "Test1", "Password", Role.ROLE_USER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Test1", result.getUsername());
        assertEquals(Role.ROLE_USER, result.getRole());
    }

    @Test
    void getUserById_whenUserNotFound_shouldThrowNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            userService.getUserById(1L);
        } catch (NotFoundException ex) {
            assertEquals("User not found", ex.getMessage());
        }
    }
}
