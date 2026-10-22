package com.envios.service.interfaceservice;

import com.envios.dto.ClienteDTOResponse;
import com.envios.dto.EnvioDTORequest;
import com.envios.dto.EnvioDTOResponse;

import java.util.List;

public interface EnvioServiceInterface {

    List<EnvioDTOResponse> traerEnvios();
    EnvioDTOResponse traerEnvio(Integer id);
    void crearEnvio(EnvioDTORequest envioDTORequest);
    EnvioDTOResponse actualizarEnvio(Integer id, EnvioDTORequest envioDTORequest);
    EnvioDTOResponse actualizarEstado(Integer id, String nuevoEstado);
    void eliminarEnvio(Integer id);

    //PARA PROBAR LOADBALANCER CON FEIGN
    List<ClienteDTOResponse> listarClientes();



}
