package com.GreenFlow.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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
        assertEquals(Role.ROLE_USER, response.getBody().getRole());
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

    @Test
    void getUserById_shouldReturnUser() {
        User user = User.builder()
                .id(1L)
                .username("Test1")
                .role(Role.ROLE_USER)
                .build();
        when(userUseCase.getUserById(1L)).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test1", response.getBody().getUsername());
        assertEquals(Role.ROLE_USER, response.getBody().getRole());
        verify(userUseCase).getUserById(1L);
    }

    @Test
    void updateUser_shouldCallServiceAndReturnUpdatedUser() {
        UserDTO dto = UserDTO.builder()
                .username("UpdatedName")
                .password("NewPassword")
                .role(Role.ROLE_ADMIN)
                .build();
        User updatedUser = User.builder()
                .id(1L)
                .username("UpdatedName")
                .role(Role.ROLE_ADMIN)
                .build();
        when(userUseCase.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        ResponseEntity<UserDTO> response = userController.updateUser(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("UpdatedName", response.getBody().getUsername());
        assertEquals(Role.ROLE_ADMIN, response.getBody().getRole());
        verify(userUseCase).updateUser(eq(1L), any(User.class));
    }

    @Test
    void deleteUser_shouldCallServiceAndReturnNoContent() {
        doNothing().when(userUseCase).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(userUseCase).deleteUser(1L);
    }
}
