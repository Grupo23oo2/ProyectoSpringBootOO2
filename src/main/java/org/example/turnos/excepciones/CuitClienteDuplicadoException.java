package org.example.turnos.excepciones;

public class CuitClienteDuplicadoException extends RuntimeException{

    public CuitClienteDuplicadoException(String mensaje) {
        super(mensaje);
    }
	
}
