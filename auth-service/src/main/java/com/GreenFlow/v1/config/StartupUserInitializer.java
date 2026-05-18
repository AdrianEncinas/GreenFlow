package com.GreenFlow.v1.config;

import com.GreenFlow.v1.application.port.out.PasswordEncoderPort;
import com.GreenFlow.v1.application.port.out.UserPersistencePort;
import com.GreenFlow.v1.domain.model.Role;
import com.GreenFlow.v1.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupUserInitializer implements ApplicationRunner {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Value("${app.bootstrap-admin.enabled:true}")
    private boolean enabled;

    @Value("${app.bootstrap-admin.username:admin}")
    private String username;

    @Value("${app.bootstrap-admin.password:admin123}")
    private String password;

    @Value("${app.bootstrap-admin.role:ROLE_ADMIN}")
    private String role;

    public StartupUserInitializer(
            UserPersistencePort userPersistencePort,
            PasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!enabled || username == null || username.isBlank() || password == null || password.isBlank()) {
            return;
        }

        if (userPersistencePort.findByUsername(username).isPresent()) {
            return;
        }

        Role selectedRole;
        try {
            selectedRole = Role.valueOf(role);
        } catch (IllegalArgumentException exception) {
            selectedRole = Role.ROLE_ADMIN;
        }

        User bootstrapUser = User.builder()
                .username(username)
                .password(passwordEncoderPort.encode(password))
                .role(selectedRole)
                .build();

        userPersistencePort.save(bootstrapUser);
    }
}
