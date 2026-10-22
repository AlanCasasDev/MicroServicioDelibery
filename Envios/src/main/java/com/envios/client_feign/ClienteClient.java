package com.envios.client_feign;

import com.envios.dto.ClienteDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "clientes-api" /*, url = "${feign.clienteapi.url}"*/) //http://localhost:9001
public interface ClienteClient {

    @GetMapping("/clientes")
    List<ClienteDTOResponse> traerClientes();

    @GetMapping("/clientes/{idcliente}")
    ClienteDTOResponse traerCliente(@PathVariable("idcliente") Integer idcliente);
}

