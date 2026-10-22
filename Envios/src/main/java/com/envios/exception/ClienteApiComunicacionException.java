package com.envios.exception;

public class ClienteApiComunicacionException extends RuntimeException {

    public ClienteApiComunicacionException(Throwable cause) {
        super("No fue posible comunicarse con la API Cliente.", cause);
    }
}
