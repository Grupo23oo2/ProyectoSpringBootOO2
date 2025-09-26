package org.example.turnos.configurations;

import org.example.turnos.servicios.implementacion.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas para HTML
                .requestMatchers(
                        "/login",
                        "/css/**",
                        "/registro/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html"
                ).permitAll()
                
                // Rutas HTML protegidas
                .requestMatchers("/clientes/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/contactos/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/email/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/empleados/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/lugares/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/servicios/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/usuarios/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/turnos/**").hasAnyRole("EMPLEADO", "ADMIN", "CLIENTE")
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // Rutas API públicas
                .requestMatchers(
                        "/api/auth/**",
                        "/api/empleados/alta-completa"
                ).permitAll()

                // Rutas API protegidas
                .requestMatchers("/api/empleados/**").hasRole("EMPLEADO")
                .requestMatchers("/api/clientes/**").hasAnyRole("EMPLEADO", "ADMIN", "CLIENTE")

                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )

            // HTML login
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )

            // API login básico
            .httpBasic(httpBasic -> httpBasic
                .realmName("API REST")
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}