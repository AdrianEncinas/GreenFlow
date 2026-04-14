package com.GreenFlow.v1.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GreenFlow.v1.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
