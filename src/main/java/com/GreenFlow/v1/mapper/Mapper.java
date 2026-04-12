package com.GreenFlow.v1.mapper;

import com.GreenFlow.v1.dto.UserDTO;
import com.GreenFlow.v1.model.User;

public class Mapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
