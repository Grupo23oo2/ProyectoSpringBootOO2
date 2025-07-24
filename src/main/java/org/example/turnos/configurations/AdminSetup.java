package org.example.turnos.configurations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.modelo.Empleado;
import org.example.turnos.modelo.Usuario;
import org.example.turnos.repositorios.IEmpleadoRepositorio;
import org.example.turnos.repositorios.IUsuarioRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class AdminSetup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired private IEmpleadoRepositorio empleadoRepositorio;
    @Autowired private IUsuarioRepositorio usuarioRepositorio;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Optional<Usuario> adminExistente = usuarioRepositorio.findByNombreUsuario("admin");

        if (adminExistente.isEmpty()) {
            Empleado empleadoExistente = empleadoRepositorio.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            Usuario usuario = new Usuario();
            usuario.setPersona(empleadoExistente); // Asociar al empleado/persona
            usuario.setNombreUsuario("admin");
            usuario.setEmail("admin@miapp.com");
            usuario.setContraseniaUsuario(passwordEncoder.encode("admin"));
            usuario.setEstado(true);
            usuario.setRole("ADMIN");
            usuario.setFechaCreacion(LocalDateTime.now());

            usuarioRepositorio.save(usuario);

            System.out.println("✅ Usuario admin creado con rol ADMIN");
        }
    }
}