package com.envios.exception;

public class EstadoEnvioInvalidoException extends RuntimeException {

    public EstadoEnvioInvalidoException(final String estado) {
        super("El estado "+estado+" no existe o esta permitido");
    }
}
