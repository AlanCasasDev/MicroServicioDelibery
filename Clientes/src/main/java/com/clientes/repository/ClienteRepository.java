package com.clientes.repository;

import com.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {


    boolean existsByDni(String dni);

    @Query("SELECT c FROM Cliente c WHERE c.dni = :dni")
    Optional<Cliente> findByDni(String dni);



}
