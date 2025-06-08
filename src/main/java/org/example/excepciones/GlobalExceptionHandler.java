package org.example.excepciones;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja MiExcepcionPersonalizada
    @ExceptionHandler(MiExcepcionPersonalizada.class)
    public String manejarMiExcepcionPersonalizada(MiExcepcionPersonalizada ex, Model model) {
        model.addAttribute("mensajeError", ex.getMessage());
        return "error/errorPersonalizado";  // carpeta /templates/error/errorPersonalizado.html
    }

    // Manejo general de otras excepciones no controladas
    @ExceptionHandler(Exception.class)
    public String manejarExcepcionGeneral(Exception ex, Model model) {
        model.addAttribute("mensajeError", "Ha ocurrido un error inesperado: " + ex.getMessage());
        return "error/errorGeneral";  // carpeta /templates/error/errorGeneral.html
    }
}