package com.clientes.exception;

public class DniInvalidoException extends RuntimeException {
    public DniInvalidoException() {
        super("Formato del DNI invalido");
    }
}
