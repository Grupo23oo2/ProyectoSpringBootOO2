package org.example.turnos.configurations;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "basicAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
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
            )
            .components(new Components()
                .addSecuritySchemes("basicAuth", new io.swagger.v3.oas.models.security.SecurityScheme()
                        .type(Type.HTTP)
                        .scheme("basic")
                        .in(In.HEADER)))
            .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }
}