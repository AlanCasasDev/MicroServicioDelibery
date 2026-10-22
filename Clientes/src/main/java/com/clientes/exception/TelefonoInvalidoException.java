package com.clientes.exception;

public class TelefonoInvalidoException extends RuntimeException {

    public TelefonoInvalidoException() {
        super("El telefono es invalido. Debe ser numerico y puede tener + al inicio");
    }
}
