package com.clientes.service.interfaceservice;

import com.clientes.dto.ClienteDTORequest;
import com.clientes.dto.ClienteDTOResponse;

import java.util.List;

public interface ClienteServiceInterface {

    List<ClienteDTOResponse> traerClientes();
    ClienteDTOResponse buscarCliente(Integer id);
    ClienteDTOResponse buscarClientePorDni(String dni);
    void crearCliente(ClienteDTORequest clienteDTORequest);
    void actualizarCliente(Integer id, ClienteDTORequest clienteDTORequest);
    ClienteDTOResponse actualizarCliente1(Integer id, ClienteDTORequest clienteDTORequest);
    void eliminarCliente(Integer id);

    void actualizarDireccionCliente(Integer id, String direccionNueva);
    void actualizarTelefonoCliente(Integer id, String telefonoNuevo);


}
