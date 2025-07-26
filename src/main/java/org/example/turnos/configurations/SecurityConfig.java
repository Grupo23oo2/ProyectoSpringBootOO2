package org.example.turnos.configurations;

import org.example.turnos.servicios.implementacion.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Habilita @PreAuthorize en tus métodos
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

            	.requestMatchers("/", "/login", "/css/**", "/js/**", "/registro/**").permitAll()

                
                .requestMatchers("/clientes/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/contactos/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/email/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/empleados/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/lugares/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/roles-usuarios/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/servicios/**").hasAnyRole("EMPLEADO", "ADMIN")
                .requestMatchers("/usuarios/**").hasAnyRole("EMPLEADO", "ADMIN")
                
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .loginProcessingUrl("/login") 
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
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