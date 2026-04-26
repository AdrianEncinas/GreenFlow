package com.GreenFlow.v1.infrastructure.adapter.out.persistence.mapper;

import com.GreenFlow.v1.domain.model.User;
import com.GreenFlow.v1.infrastructure.adapter.out.persistence.entity.UserEntity;

public final class UserPersistenceMapper {

    private UserPersistenceMapper() {
    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .role(entity.getRole())
                .build();
    }

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole());
    }
}