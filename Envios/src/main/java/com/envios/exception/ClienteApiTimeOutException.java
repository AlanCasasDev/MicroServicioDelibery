package com.envios.exception;

public class ClienteApiTimeOutException extends RuntimeException {
    public ClienteApiTimeOutException(Throwable cause) {
        super("Se agotó el tiempo de espera al comunicarse con la API Cliente.", cause);
    }
}
