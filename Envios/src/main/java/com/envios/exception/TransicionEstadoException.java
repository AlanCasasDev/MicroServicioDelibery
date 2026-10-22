package com.envios.exception;

import com.envios.model.enum_envio.EnumEstadoEnvio;

public class TransicionEstadoException extends RuntimeException {

    public TransicionEstadoException(EnumEstadoEnvio estadoAnterior, EnumEstadoEnvio estadoNuevo){
        super("Error! No se permite la transicion de estado "+estadoAnterior.getNombre()+ " a "+estadoNuevo.getNombre());
    }
}
