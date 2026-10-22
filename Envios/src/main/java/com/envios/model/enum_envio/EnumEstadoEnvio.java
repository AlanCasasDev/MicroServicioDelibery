package com.envios.model.enum_envio;

import com.envios.exception.EstadoEnvioInvalidoException;

public enum EnumEstadoEnvio {

    PENDIENTE("Pendiente","La venta fue creada pero todavía no se confirmó el envio."),
    PREPARANDO("Preparando","Se esta preparando el envio."),
    LISTO_PARA_ENVIAR("Listo para enviar o retirar","El pedido está listo para retirar o enviar."),
    EN_CAMINO("En camino","El envio esta en camino al cliente."),
    ENTREGADO("Entregado", "El envio fue entregado correctamete."),
    CANCELADO("Cancelado", "El envio fue cancelado."),
    RECHAZADO("Rechazado", "El envio llego, pero fue rechazado por el cliente."),
    REEMBOLSADO("Reembolsado", "Se devolvió el dinero al cliente.");


    private final String descripcion;
    private final String nombre;


    EnumEstadoEnvio(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public static EnumEstadoEnvio desdeString(String estado) {

        if (estado == null || estado.isBlank()) {
            throw new EstadoEnvioInvalidoException(estado);
        }

        try {
            return EnumEstadoEnvio.valueOf(
                    estado.toUpperCase()
                            .trim()
                            .replaceAll("\\s+", "_")
            );

        } catch (IllegalArgumentException e) {
            throw new EstadoEnvioInvalidoException(estado);
        }

    }

}
