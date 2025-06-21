package org.example.turnos.excepciones;

public class DniClienteDuplicadoException extends RuntimeException {
    public DniClienteDuplicadoException(String mensaje) {
        super(mensaje);
    }
}