package org.example.turnos.excepciones;

public class MiExcepcionPersonalizada extends RuntimeException {
    public MiExcepcionPersonalizada(String mensaje) {
        super(mensaje);
    }
}
