package com.GreenFlow.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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

import com.GreenFlow.v1.dto.UserDTO;
import com.GreenFlow.v1.model.Role;
import com.GreenFlow.v1.service.IUserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private IUserService userService;

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
        when(userService.createUser(any(UserDTO.class))).thenReturn(dto);

        ResponseEntity<UserDTO> response = userController.createUser(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(dto, response.getBody());
        verify(userService).createUser(any());
    }

    @Test
    void getAllUsers_shouldReturnUserList() {
        UserDTO dto = UserDTO.builder()
                .id(1L)
                .username("Test1")
                .password("Password")
                .role(Role.ROLE_USER)
                .build();
        when(userService.getAllUsers()).thenReturn(List.of(dto));

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Test1", response.getBody().get(0).getUsername());
        verify(userService).getAllUsers();
    }
}
