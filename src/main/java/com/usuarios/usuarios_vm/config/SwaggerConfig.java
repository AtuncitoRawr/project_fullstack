package com.usuarios.usuarios_vm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API 2025 Creacion de usuarios")
                        .version("1.0")
                        .description("Documentacion de la API para el sistema de creacion de usuarios"));
    }
}
