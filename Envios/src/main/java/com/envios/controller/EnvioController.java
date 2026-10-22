package com.envios.controller;

import com.envios.dto.ClienteDTOResponse;
import com.envios.dto.EnvioDTORequest;
import com.envios.dto.EnvioDTOResponse;
import com.envios.service.interfaceservice.EnvioServiceInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/envios")
public class EnvioController {

    @Value("${server.port}")
    private Integer serverPort;
    private final EnvioServiceInterface envioService;

    public EnvioController(EnvioServiceInterface envioService) {
        this.envioService = envioService;
    }


    //http://localhost:9002/envios
    @GetMapping
    public ResponseEntity<List<EnvioDTOResponse>> traerEnvios() {
        IO.println("Solicitud GET: \"traerEnvios\" en el puerto: " + serverPort);
        return ResponseEntity.ok(envioService.traerEnvios());
    }

    //http://localhost:9002/envios/1
    @GetMapping("/{idenvio}")
    public ResponseEntity<EnvioDTOResponse> traerEnvio(@PathVariable("idenvio") @Positive Integer id) {
        IO.println("Solicitud GET: \"traerEnvio\" en el puerto: " + serverPort);
        return ResponseEntity.ok(envioService.traerEnvio(id));
    }

    //http://localhost:9002/envios/crear-envio
    @PostMapping("/crear-envio")
    public ResponseEntity<String> crearEnvio(@Valid @RequestBody EnvioDTORequest envioDTORequest) {

        IO.println("Solicitud POST: \"crearEnvio\" en el puerto: " + serverPort);
        envioService.crearEnvio(envioDTORequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("Envio creado correctamente");
    }

    //http://localhost:9002/envios/3
    @PutMapping("/{id}")
    public ResponseEntity<EnvioDTOResponse> actualizarEnvio(@PathVariable @Positive Integer id,
                                                            @Valid @RequestBody EnvioDTORequest envioDTORequest) {

        IO.println("Solicitud PUT: \"actualizarEnvio\" en el puerto: " + serverPort);
        return ResponseEntity.ok(envioService.actualizarEnvio(id, envioDTORequest));
    }

    //http://localhost:9002/envios/2/actualizar-estado?nuevoEstado=listo_para_enviar
    @PatchMapping("/{id}/actualizar-estado")
    public ResponseEntity<EnvioDTOResponse> actualizarEstadoEnvio(@PathVariable @Positive Integer id,
                                                                  @RequestParam @NotBlank String nuevoEstado) {

        IO.println("Solicitud PUT: \"actualizarEstadoEnvio\" en el puerto: " + serverPort);
        return ResponseEntity.ok(envioService.actualizarEstado(id, nuevoEstado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEnvio(@PathVariable @Positive Integer id) {
        envioService.eliminarEnvio(id);

        IO.println("Solicitud DELETE: \"eliminarEnvio\" en el puerto: " + serverPort);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Envio eliminado correctamente");
    }


    //PARA PROBAR LOADBALANCER CON FEIGN
    @GetMapping("/listaDeClientes")
    public ResponseEntity<List<ClienteDTOResponse>> listarClientes(){

        IO.println("Solicitud GET: \"listarClientes\" en el puerto: " + serverPort);

        return ResponseEntity.status(HttpStatus.OK).body(envioService.listarClientes());
    }
}
