package com.GreenFlow.v1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI greenFlowOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GreenFlow Auth Service API")
                        .description("API REST para la gestión de usuarios del servicio de autenticación de GreenFlow")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("GreenFlow Team")));
    }
}
