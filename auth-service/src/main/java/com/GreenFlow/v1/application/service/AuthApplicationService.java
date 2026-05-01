package com.GreenFlow.v1.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GreenFlow.v1.application.port.out.PasswordEncoderPort;
import com.GreenFlow.v1.application.port.out.UserPersistencePort;
import com.GreenFlow.v1.domain.exception.InvalidCredentialsException;
import com.GreenFlow.v1.domain.model.User;

@Service
@Transactional(readOnly = true)
public class AuthApplicationService {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final JwtService jwtService;

    public AuthApplicationService(
            UserPersistencePort userPersistencePort,
            PasswordEncoderPort passwordEncoderPort,
            JwtService jwtService) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.jwtService = jwtService;
    }

    public String login(String username, String password) {
        User user = userPersistencePort.findByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoderPort.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return jwtService.generateToken(user);
    }
}
