package org.example.turnos.excepciones;

public class DniEmpleadoDuplicadoException extends RuntimeException {
    public DniEmpleadoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
