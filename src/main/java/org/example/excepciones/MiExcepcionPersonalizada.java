package org.example.excepciones;

public class MiExcepcionPersonalizada extends RuntimeException {
    public MiExcepcionPersonalizada(String mensaje) {
        super(mensaje);
    }
}
