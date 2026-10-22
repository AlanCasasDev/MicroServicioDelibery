package com.clientes.exception;

public class ClienteDniNoEncontradoException extends RuntimeException {

    public ClienteDniNoEncontradoException(String dni) {
        super("No se encuentra un Cliente con dni: " +dni+" en la base de datos ");
    }
}
