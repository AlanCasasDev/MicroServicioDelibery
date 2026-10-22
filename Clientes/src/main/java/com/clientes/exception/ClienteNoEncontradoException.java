package com.clientes.exception;

public class ClienteNoEncontradoException extends RuntimeException {

    public ClienteNoEncontradoException(Integer id) {
        super("No se encuentra un Cliente con id " +id+" en la base de datos ");
    }
}
