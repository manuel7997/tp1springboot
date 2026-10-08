package com.example.demo.exception;

/** La operación choca con el estado actual de los datos (409 Conflict). */
public class ConflictoException extends RuntimeException {
    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
