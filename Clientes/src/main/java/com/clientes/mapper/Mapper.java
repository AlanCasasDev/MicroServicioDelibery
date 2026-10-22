package com.clientes.mapper;

import com.clientes.dto.ClienteDTORequest;
import com.clientes.dto.ClienteDTOResponse;
import com.clientes.model.Cliente;

public class Mapper {


    //Map de Client a ClienteDTORequest (TODO no tiene sentido este caso)
    public static ClienteDTORequest toClienteDTOResquest(Cliente c){

        if(c==null) {
            return null;
        }

        ClienteDTORequest clienteDTORequest = ClienteDTORequest.builder()
                .dni(c.getDni())
                .nombre(c.getNombre())
                .apellido(c.getApellido())
                .direccion(c.getDireccion())
                .telefono(c.getTelefono())
                .build();

        return clienteDTORequest;
    }


    //Map de Client a ClienteDTOResponse
    public static ClienteDTOResponse toClienteDTOResponse(Cliente c){

        if(c==null) {
            return null;
        }

        ClienteDTOResponse clienteDTOResponse = ClienteDTOResponse.builder()
                .id(c.getId())
                .dni(c.getDni())
                .nombre(c.getNombre())
                .apellido(c.getApellido())
                .direccion(c.getDireccion())
                .telefono(c.getTelefono())
                .build();

        return clienteDTOResponse;
    }


    //Map de ClienteDTORequest a Client
    public static Cliente toCliente(ClienteDTORequest c){

        if(c==null) {
            return null;
        }

        Cliente cliente = Cliente.builder()
                .dni(c.getDni())
                .nombre(c.getNombre())
                .apellido(c.getApellido())
                .direccion(c.getDireccion())
                .telefono(c.getTelefono())
                .build();

        return cliente;
    }

}
