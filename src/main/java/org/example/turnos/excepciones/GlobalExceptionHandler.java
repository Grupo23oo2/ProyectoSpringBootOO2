package org.example.turnos.excepciones;

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

    @ExceptionHandler(CuitClienteDuplicadoException.class)
    public String manejarRecursoNoEncontrado(CuitClienteDuplicadoException ex, Model model) {
        model.addAttribute("mensajeError", ex.getMessage());
        return "error/errorCuitClienteDuplicado";
    }
    
    @ExceptionHandler(DniEmpleadoDuplicadoException.class)
    public String manejarDniDuplicado(DniEmpleadoDuplicadoException ex, Model model) {
        model.addAttribute("mensajeError", ex.getMessage());
        return "error/errorDniEmpleadoDuplicado";  
    }
    
    @ExceptionHandler(DniClienteDuplicadoException.class)
    public String manejarDniClienteDuplicado(DniClienteDuplicadoException ex, Model model) {
        model.addAttribute("mensajeError", ex.getMessage());
        return "error/errorDniClienteDuplicado"; 
    }
    
    @ExceptionHandler(DireccionLugarDuplicadaException.class)
    public String manejarDireccionLugarDuplicada(DireccionLugarDuplicadaException ex, Model model) {
        model.addAttribute("mensajeError", ex.getMessage());
        return "error/errorDireccionLugarDuplicada";
    }
    
    
    // Manejo general de otras excepciones no controladas
    @ExceptionHandler(Exception.class)
    public String manejarExcepcionGeneral(Exception ex, Model model) {
        model.addAttribute("mensajeError", "Ha ocurrido un error inesperado: " + ex.getMessage());
        return "error/errorGeneral";  // carpeta /templates/error/errorGeneral.html
    }
}