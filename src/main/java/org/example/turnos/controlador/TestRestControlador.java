package org.example.turnos.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestRestControlador {

    @GetMapping
    public String holaSwagger() {
        return "Hola desde Swagger!";
    }
}