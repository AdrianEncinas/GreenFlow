package com.GreenFlow.v1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GreenFlow.v1.dto.UserDTO;
import com.GreenFlow.v1.model.User;
import com.GreenFlow.v1.service.IUserService;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    public final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    //get usuarios
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        final User user = new User(null,userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole());
        userService.createUser(user);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        final User user = new User(null,userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole());
        userService.updateUser(id, user);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
