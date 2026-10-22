package com.envios.service;

import com.envios.client_feign.ClienteClient;
import com.envios.dto.ClienteDTOResponse;
import com.envios.dto.EnvioDTORequest;
import com.envios.dto.EnvioDTOResponse;
import com.envios.exception.*;
import com.envios.mapper.Mapper;
import com.envios.model.Envio;
import com.envios.model.enum_envio.EnumEstadoEnvio;
import com.envios.repository.EnvioRepository;
import com.envios.service.interfaceservice.EnvioServiceInterface;
import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class EnvioService implements EnvioServiceInterface {

    private final EnvioRepository envioRepository;
    private final ClienteClient clienteClient;

    public EnvioService(EnvioRepository envioRepository, ClienteClient clienteClient) {
        this.envioRepository = envioRepository;
        this.clienteClient = clienteClient;
    }



    @Override
    @Transactional(readOnly = true)
    public List<EnvioDTOResponse> traerEnvios() {

        List<EnvioDTOResponse> envios =
                envioRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                        .stream()
                        .map(Mapper::toEnvioDTOResponse)
                        .toList();

        return envios;
    }

    @Override
    @Transactional(readOnly = true)
    public EnvioDTOResponse traerEnvio(Integer id) {
        return Mapper.toEnvioDTOResponse(obtenerEnvio(id));
    }

    @Override
    @Transactional
    public void crearEnvio(EnvioDTORequest envioDTORequest) {

        ClienteDTOResponse cliente = obtenerCliente(envioDTORequest.getIdCliente()); //valida q exista el cliente y lo guarda

        /*Envio envio = Envio.builder()
                .estado(envioDTORequest.getEstado())
                .descripcion(envioDTORequest.getDescripcion())
                .fechaDeCreacion(LocalDateTime.now())
                .idCliente(cliente.getId())
                .build();*/

        Envio envio = Mapper.toEnvio(envioDTORequest);
        envio.setFechaDeCreacion(LocalDateTime.now());

        envioRepository.save(envio);

    }

    @Override
    @Transactional
    public EnvioDTOResponse actualizarEnvio(Integer id, EnvioDTORequest envioDTORequest) {

        //Valida que exista el envio que se quiere actualizar
        Envio envio = obtenerEnvio(id);

        //Validar que el cliente exista primero
        ClienteDTOResponse cliente = obtenerCliente(envioDTORequest.getIdCliente());

        //Validar que el cambio de estado sea permitido
        validarTransicionEstado(envio.getEstado(), envioDTORequest.getEstado());

        envio.setDescripcion(envioDTORequest.getDescripcion());
        envio.setEstado(envioDTORequest.getEstado());
        envio.setIdCliente(cliente.getId()); // o envioDTORequest.getIdCliente()

        envioRepository.save(envio);

        return Mapper.toEnvioDTOResponse(envio);
    }

    @Override
    @Transactional
    public EnvioDTOResponse actualizarEstado(Integer id, String nuevoEstado) {

        Envio envio = obtenerEnvio(id);
        EnumEstadoEnvio estado = EnumEstadoEnvio.desdeString(nuevoEstado);

        validarTransicionEstado(envio.getEstado(), estado);

        envio.setEstado(estado);

        envioRepository.save(envio);
        return Mapper.toEnvioDTOResponse(envio);
    }

    @Override
    @Transactional
    public void eliminarEnvio(Integer id) {

        Envio envio = obtenerEnvio(id); //Con esto nos aseguramos de que el envio exista primero

        envioRepository.delete(envio);
    }

    //PARA PROBAR LOADBALANCER CON FEIGN Y RESILIENCE
    @Override
    @CircuitBreaker(name = "api_clientes", fallbackMethod = "fallback")
    @Retry(name = "api_clientes", fallbackMethod = "fallback")
    @RateLimiter(name = "api_clientes", fallbackMethod = "fallback")
    @Bulkhead(name = "api_clientes", fallbackMethod = "fallback")
    //@TimeLimiter(name = "api_clientes", fallbackMethod = "fallback") //Requiere una operación asíncrona (CompletionStage)
    public List<ClienteDTOResponse> listarClientes(){
        IO.println(">>> Intentando llamar a ClientesApi");
        return clienteClient.traerClientes();
    }

    //FALLBACK PARA PRUEBAS DE RESILIENCE4J
    private List<ClienteDTOResponse> fallback(Exception e) {

        System.out.println("Fallback ejecutado: " + e.getClass().getSimpleName());
        System.out.println("Mensaje: " + e.getMessage());

        return List.of();
    }


    //Metodos helpers

    private Envio obtenerEnvio(Integer id) {
        Envio envio = envioRepository.findById(id).orElseThrow(() -> new EnvioNoEncontradoException(id));
        return envio;
    }


    private ClienteDTOResponse obtenerCliente(Integer id) {

        try {
            return clienteClient.traerCliente(id); //puede retornar un 404 sino encuentra el cliente. No devuelve null
        } catch (FeignException.NotFound e) {
            throw new ClienteNoEncontradoException(id);
        } catch (FeignException.ServiceUnavailable e) {
            throw new ClienteApiNoDisponibleException(e);
        } catch (FeignException.GatewayTimeout e) {
            throw new ClienteApiTimeOutException(e);
        } catch (RetryableException e) {
            throw new ClienteApiComunicacionException(e);
        } catch (FeignException e) {
            throw new ClienteApiException(e);
        }
    }

    private void validarTransicionEstado(EnumEstadoEnvio estadoAnterior, EnumEstadoEnvio estadoNuevo) {
        if (estadoAnterior == estadoNuevo) {
            return;
        }

        if (!transcionEstadoPermitida(estadoAnterior, estadoNuevo)) {
            throw new TransicionEstadoException(estadoAnterior, estadoNuevo);
        }
    }

    private boolean transcionEstadoPermitida(EnumEstadoEnvio estadoAnterior, EnumEstadoEnvio estadoNuevo) {
        if (estadoAnterior == null || estadoNuevo == null) {
            return false; //anteriormente true
        }

        return switch (estadoAnterior) {

            case PENDIENTE -> Set.of(
                    EnumEstadoEnvio.PREPARANDO,
                    EnumEstadoEnvio.CANCELADO
            ).contains(estadoNuevo);

            case PREPARANDO -> Set.of(
                    EnumEstadoEnvio.LISTO_PARA_ENVIAR,
                    EnumEstadoEnvio.CANCELADO
            ).contains(estadoNuevo);

            case LISTO_PARA_ENVIAR -> Set.of(
                    EnumEstadoEnvio.EN_CAMINO,
                    EnumEstadoEnvio.CANCELADO
            ).contains(estadoNuevo);

            case EN_CAMINO -> Set.of(
                    EnumEstadoEnvio.ENTREGADO,
                    EnumEstadoEnvio.RECHAZADO
            ).contains(estadoNuevo);

            case ENTREGADO -> Set.of(
                    EnumEstadoEnvio.REEMBOLSADO
            ).contains(estadoNuevo);

            case CANCELADO -> Set.of(
                    EnumEstadoEnvio.REEMBOLSADO
            ).contains(estadoNuevo);

            case RECHAZADO -> Set.of(
                    EnumEstadoEnvio.REEMBOLSADO
            ).contains(estadoNuevo);

            case REEMBOLSADO -> false;
        };
    }


}
