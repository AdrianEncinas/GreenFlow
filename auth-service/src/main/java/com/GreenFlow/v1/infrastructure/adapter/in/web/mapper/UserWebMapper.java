package com.GreenFlow.v1.infrastructure.adapter.in.web.mapper;

import com.GreenFlow.v1.domain.model.User;
import com.GreenFlow.v1.infrastructure.adapter.in.web.dto.UserDTO;

public final class UserWebMapper {

    private UserWebMapper() {
    }

    public static UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public static User toDomain(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();
    }
}