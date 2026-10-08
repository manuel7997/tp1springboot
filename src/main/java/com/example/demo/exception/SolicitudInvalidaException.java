package com.example.demo.exception;

/** La solicitud es sintácticamente válida pero no tiene sentido de negocio (400 Bad Request). */
public class SolicitudInvalidaException extends RuntimeException {
    public SolicitudInvalidaException(String mensaje) {
        super(mensaje);
    }
}
