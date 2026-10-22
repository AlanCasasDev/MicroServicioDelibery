package com.envios.mapper;

import com.envios.dto.EnvioDTORequest;
import com.envios.dto.EnvioDTOResponse;
import com.envios.model.Envio;
import com.envios.model.enum_envio.EnumEstadoEnvio;

public class Mapper {


    public static EnvioDTOResponse toEnvioDTOResponse(Envio envio) {

        if (envio == null) {
            return null;
        }

        EnvioDTOResponse respuesta = EnvioDTOResponse.builder()
                .id(envio.getId())
                .descripcion(envio.getDescripcion())
                .estado(envio.getEstado())
                .fechaDeCreacion(envio.getFechaDeCreacion())
                .idCliente(envio.getIdCliente())
                .build();

        return respuesta;
    }


    public static EnvioDTORequest toEnvioDTORequest(Envio envio) {

        if (envio == null) {
            return null;
        }

        EnvioDTORequest peticion = EnvioDTORequest.builder()
                //.fechaDeCreacion(envio.getFechaDeCreacion())
                .descripcion(envio.getDescripcion())
                .estado(envio.getEstado())
                .idCliente(envio.getIdCliente())
                .build();

        return peticion;
    }


    public static Envio toEnvio(EnvioDTORequest envioDTORequest) {

        if (envioDTORequest == null) {
            return null;
        }

        Envio envio = Envio.builder()
                //.fechaDeCreacion(envioDTORequest.getFechaDeCreacion())
                .descripcion(envioDTORequest.getDescripcion())
                .estado(envioDTORequest.getEstado())
                .idCliente(envioDTORequest.getIdCliente())
                .build();

        return envio;
    }

}
