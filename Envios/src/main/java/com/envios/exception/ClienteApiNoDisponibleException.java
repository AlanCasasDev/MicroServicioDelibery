package com.envios.exception;

public class ClienteApiNoDisponibleException extends RuntimeException {

    public ClienteApiNoDisponibleException(Throwable cause) {
        super("La API Cliente no se encuentra disponible.", cause);
    }
}
