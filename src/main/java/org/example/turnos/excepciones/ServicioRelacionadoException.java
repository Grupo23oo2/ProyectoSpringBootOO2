package org.example.turnos.excepciones;

public class ServicioRelacionadoException extends RuntimeException {
    public ServicioRelacionadoException(String mensaje) {
        super(mensaje);
    }
}