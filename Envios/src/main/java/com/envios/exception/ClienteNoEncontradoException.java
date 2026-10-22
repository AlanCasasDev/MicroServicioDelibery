package com.envios.exception;

public class ClienteNoEncontradoException extends RuntimeException {

    public ClienteNoEncontradoException(Integer id) {
        super("No se encuentra el cliente con el idCliente: " + id);
    }
}
