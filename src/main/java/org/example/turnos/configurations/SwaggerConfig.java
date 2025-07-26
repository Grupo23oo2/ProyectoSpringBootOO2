package org.example.turnos.configurations;

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
                .title("Sistema de Gestión de Turnos")
                .version("1.0.0")
                .description("API REST para la administración de turnos y usuarios.\n\n"
                        + "Equipo 23\n"
                        + "Repositorio: https://github.com/Grupo23oo2/ProyectoSpringBootOO2")
            );
    }
}