package com.envios.exception;

public class EnvioNoEncontradoException extends RuntimeException {

    public EnvioNoEncontradoException(Integer id) {
        super("No se encuentra un envio con el id: " + id);
    }

}
