package com.clientes.controller;

import com.clientes.dto.ClienteDTORequest;
import com.clientes.dto.ClienteDTOResponse;
import com.clientes.service.interfaceservice.ClienteServiceInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Validated
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Value("${server.port}")
    private Integer serverPort;
    private final ClienteServiceInterface clienteService;


    public ClienteController(ClienteServiceInterface clienteService) {
        this.clienteService = clienteService;
    }



    //http://localhost:9001/clientes
    @GetMapping
    public ResponseEntity<List<ClienteDTOResponse>> traerClientes(){

        //IO.println("Solicitud GET: \"traerClientes\" en el puerto: " + serverPort);
        IO.println(
                "Solicitud GET: \"traerClientes\" | Puerto: "
                        + serverPort
                        + " | Hora: "
                        + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"))
        );
        return ResponseEntity.ok(clienteService.traerClientes());
    }

    //http://localhost:9001/clientes/1
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTOResponse> traerCliente(@PathVariable @Positive Integer id){

        IO.println("Solicitud GET: \"traerCliente\" en el puerto: " + serverPort);
        return ResponseEntity.ok(clienteService.buscarCliente(id));
    }

    //http://localhost:9001/clientes/buscar-por-dni/34310152
    @GetMapping("/buscar-por-dni/{dni}")
    public ResponseEntity<ClienteDTOResponse> traerClientePorDni(
            @PathVariable @NotBlank @Pattern(regexp = "^\\d{8,9}$") String dni){

        IO.println("Solicitud GET: \"traerClientePorDni\" en el puerto: " + serverPort);
        return ResponseEntity.ok(clienteService.buscarClientePorDni(dni));
    }

    //http://localhost:9001/clientes/crear-cliente
    @PostMapping("/crear-cliente")
    public ResponseEntity<String> crearCliente(@Valid @RequestBody ClienteDTORequest clienteDTORequest){

        IO.println("Solicitud POST: \"crearCliente\" en el puerto: " + serverPort);
        clienteService.crearCliente(clienteDTORequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Cliente creado correctamente");
    }

    //http://localhost:9001/clientes/2
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTOResponse> actualizarClienteModern(
            @PathVariable @Positive Integer id,
            @Valid @RequestBody ClienteDTORequest clienteDTORequest){

        IO.println("Solicitud PUT: \"actualizarClienteModern\" en el puerto: " + serverPort);
        ClienteDTOResponse clienteDTOResponse = clienteService.actualizarCliente1(id, clienteDTORequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteDTOResponse);

        // return ResponseEntity.ok(clienteDTOResponse); //Es equivalente

    }

    //http://localhost:9001/clientes/2/antiguo
    @PutMapping("/{id}/antiguo")
    public ResponseEntity<String> actualizarCliente(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteDTORequest clienteDTORequest){

        IO.println("Solicitud PUT: \"actualizarCliente\" en el puerto: " + serverPort);
        clienteService.actualizarCliente(id, clienteDTORequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Cliente actualizado correctamente");
    }

    //http://localhost:9001/clientes/2
    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable @Positive Integer id){

        IO.println("Solicitud DELETE: \"eliminarCliente\" en el puerto: " + serverPort);
        clienteService.eliminarCliente(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Cliente eliminado correctamente");
    }

    //http://localhost:9001/clientes/1/actualizar-direccion?direccion=45-331
    @PatchMapping("/{id}/actualizar-direccion")
    public ResponseEntity<String> actualizardireccion(
            @PathVariable @Positive Integer id,
            @RequestParam @NotBlank String direccion){

        IO.println("Solicitud PATCH: \"actualizarDireccion\" en el puerto: " + serverPort);
        clienteService.actualizarDireccionCliente(id,direccion);

        return ResponseEntity.status(HttpStatus.OK)
                .body("La direccion del Cliente ha sido actualizada correctamente");
    }

    //http://localhost:9001/clientes/1/actualizar-telefono?telefono=+5492216108551
    @PatchMapping("/{id}/actualizar-telefono")
    public ResponseEntity<String> actualizarTelefono(
            @PathVariable @Positive Integer id,
            @RequestParam @NotBlank @Pattern(regexp = "^\\+?[0-9]{8,15}$") String telefono){

        IO.println("Solicitud PATCH: \"actualizarTelefono\" en el puerto: " + serverPort);
        clienteService.actualizarTelefonoCliente(id,telefono);

        return ResponseEntity.status(HttpStatus.OK)
                .body("El telefono del Cliente ha sido actualizado correctamente");
    }


}
