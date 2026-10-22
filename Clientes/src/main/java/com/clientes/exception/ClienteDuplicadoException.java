package com.clientes.exception;

public class ClienteDuplicadoException extends RuntimeException{
    public ClienteDuplicadoException(String dni) {
        super("Cliente duplicado: Ya existe un cliente con el dni " + dni);
    }
}
