package com.GreenFlow.v1.infrastructure.adapter.in.web;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GreenFlow.v1.application.port.in.UserUseCase;
import com.GreenFlow.v1.infrastructure.adapter.in.web.dto.UserDTO;
import com.GreenFlow.v1.infrastructure.adapter.in.web.mapper.UserWebMapper;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Operaciones de gestion de usuarios")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @Operation(summary = "Listar todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userUseCase.getAllUsers().stream()
                .map(UserWebMapper::toDto)
                .toList();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        UserDTO user = UserWebMapper.toDto(userUseCase.getUserById(id));
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponse(responseCode = "200", description = "Usuario creado correctamente")
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = UserWebMapper.toDto(userUseCase.createUser(UserWebMapper.toDomain(userDTO)));
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Actualizar un usuario existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = UserWebMapper.toDto(userUseCase.updateUser(id, UserWebMapper.toDomain(userDTO)));
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Eliminar un usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}