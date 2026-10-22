package com.clientes.service;

import com.clientes.dto.ClienteDTORequest;
import com.clientes.dto.ClienteDTOResponse;
import com.clientes.exception.ClienteDniNoEncontradoException;
import com.clientes.exception.ClienteDuplicadoException;
import com.clientes.exception.ClienteNoEncontradoException;
import com.clientes.exception.TelefonoInvalidoException;
import com.clientes.mapper.Mapper;
import com.clientes.model.Cliente;
import com.clientes.repository.ClienteRepository;
import com.clientes.service.interfaceservice.ClienteServiceInterface;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class ClienteService implements ClienteServiceInterface {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }



    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTOResponse> traerClientes() {

        List<ClienteDTOResponse> clientes =
                clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(Mapper::toClienteDTOResponse)
                .toList();


        return clientes;
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTOResponse buscarCliente(Integer id) {
        return Mapper.toClienteDTOResponse(obtenerCliente(id));
    }


    @Override
    @Transactional(readOnly = true)
    public ClienteDTOResponse buscarClientePorDni(String dni) {
        return Mapper.toClienteDTOResponse(obtenerClientePorDni(dni));
    }



    @Override
    @Transactional
    public void crearCliente(ClienteDTORequest clienteDTORequest) {

        validarDniDuplicado(clienteDTORequest.getDni());

        clienteRepository.save(Mapper.toCliente(clienteDTORequest));

    }


    @Override
    @Transactional
    public void actualizarCliente(Integer id, ClienteDTORequest clienteDTORequest) {

        Cliente cliente =  obtenerCliente(id);

        /*"Si el DNI actual del cliente es diferente al DNI que me están enviando,
        entonces significa que están intentando cambiarlo.
        En ese caso, verifico que el nuevo DNI no pertenezca a otro cliente."*/
        if (!Objects.equals(cliente.getDni(), clienteDTORequest.getDni())) {
            validarDniDuplicado(clienteDTORequest.getDni());
        } //evita que el sistema considere duplicado el DNI del propio cliente que estás actualizando.

        cliente.setDni(clienteDTORequest.getDni());
        cliente.setNombre(clienteDTORequest.getNombre());
        cliente.setApellido(clienteDTORequest.getApellido());
        cliente.setTelefono(clienteDTORequest.getTelefono());
        cliente.setDireccion((clienteDTORequest.getDireccion()));

        clienteRepository.save(cliente);

    }


    @Override
    @Transactional
    public ClienteDTOResponse actualizarCliente1(Integer id, ClienteDTORequest clienteDTORequest) {

        Cliente cliente =  obtenerCliente(id);

        /*"Si el DNI actual del cliente es diferente al DNI que me están enviando,
        entonces significa que están intentando cambiarlo.
        En ese caso, verifico que el nuevo DNI no pertenezca a otro cliente."*/
        if (!Objects.equals(cliente.getDni(), clienteDTORequest.getDni())) {
            validarDniDuplicado(clienteDTORequest.getDni());
        } //evita que el sistema considere duplicado el DNI del propio cliente que estás actualizando.

        cliente.setDni(clienteDTORequest.getDni());
        cliente.setNombre(clienteDTORequest.getNombre());
        cliente.setApellido(clienteDTORequest.getApellido());
        cliente.setTelefono(clienteDTORequest.getTelefono());
        cliente.setDireccion((clienteDTORequest.getDireccion()));

        clienteRepository.save(cliente);

        ClienteDTOResponse clienteDTOResponse = Mapper.toClienteDTOResponse(cliente);

        return clienteDTOResponse;

    }

    @Override
    @Transactional
    public void eliminarCliente(Integer id) {

        Cliente cliente =  obtenerCliente(id);

        clienteRepository.delete(cliente);
    }

    @Override
    @Transactional
    public void actualizarDireccionCliente(Integer id, String direccionNueva) {

        Cliente c =  obtenerCliente(id);
        c.setDireccion(direccionNueva);

        clienteRepository.save(c);

    }

    @Override
    @Transactional
    public void actualizarTelefonoCliente(Integer id, String telefonoNuevo) {

        Cliente c =  obtenerCliente(id);

        if (!TELEFONO_PATTERN.matcher(telefonoNuevo).matches()) {
            throw new TelefonoInvalidoException();
        }

        c.setTelefono(telefonoNuevo);

        clienteRepository.save(c);

    }


    //-------------------------------------HELPERS (Variables Patron y metodos)-----------------------------------

    //Variable que determina el formato que debe tener un telefono. Es un patron
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^\\+?[0-9]{8,15}$");
    private static final Pattern DNI_PATTERN = Pattern.compile("^\\d{8,9}$");


    private Cliente obtenerCliente(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ClienteNoEncontradoException(id));
    }

    private Cliente obtenerClientePorDni(String dni) {
        return clienteRepository.findByDni(dni).orElseThrow(() -> new ClienteDniNoEncontradoException(dni));
    }

    private void validarDniDuplicado(String dni){

        if(clienteRepository.existsByDni(dni)){
            throw new ClienteDuplicadoException(dni);
        }
    }

}
