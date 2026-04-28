package com.GreenFlow.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.GreenFlow.v1.application.port.in.UserUseCase;
import com.GreenFlow.v1.domain.model.Role;
import com.GreenFlow.v1.domain.model.User;
import com.GreenFlow.v1.infrastructure.adapter.in.web.UserController;
import com.GreenFlow.v1.infrastructure.adapter.in.web.dto.UserDTO;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserUseCase userUseCase;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser_shouldCallServiceAndReturnOk() {
        UserDTO dto = UserDTO.builder()
                .id(null)
                .username("Test1")
                .password("Password")
                .role(Role.ROLE_USER)
                .build();
        User createdUser = User.builder()
            .id(1L)
            .username("Test1")
            .role(Role.ROLE_USER)
            .build();
        when(userUseCase.createUser(any(User.class))).thenReturn(createdUser);

        ResponseEntity<UserDTO> response = userController.createUser(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test1", response.getBody().getUsername());
        verify(userUseCase).createUser(any(User.class));
    }

    @Test
    void getAllUsers_shouldReturnUserList() {
        User user = User.builder()
            .id(1L)
            .username("Test1")
            .role(Role.ROLE_USER)
                .build();
        when(userUseCase.getAllUsers()).thenReturn(List.of(user));

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Test1", response.getBody().get(0).getUsername());
        verify(userUseCase).getAllUsers();
    }
}
