package com.GreenFlow.v1.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.GreenFlow.v1.application.port.out.UserPersistencePort;
import com.GreenFlow.v1.domain.model.User;
import com.GreenFlow.v1.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.GreenFlow.v1.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.GreenFlow.v1.infrastructure.adapter.out.persistence.repository.UserJpaRepository;

@Component
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserJpaRepository userJpaRepository;

    public UserPersistenceAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll().stream()
                .map(UserPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id)
                .map(UserPersistenceMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity persistedUser = userJpaRepository.save(UserPersistenceMapper.toEntity(user));
        return UserPersistenceMapper.toDomain(persistedUser);
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(UserPersistenceMapper.toEntity(user));
    }
}