package com.clientes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Cliente {

    //id, dni, nombre, apellido, direccion y num de telefono
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) //impide que este campo sea seteable
    private Integer id;

    @Column(unique = true,  nullable = false,  length = 9)
    private String dni;

    @Column(nullable = false,  length = 25)
    private String nombre;

    @Column(nullable = false,  length = 25)
    private String apellido;

    @Column(nullable = false,  length = 50)
    private String direccion;

    @Column(nullable = false,  length = 17) //ej + 54 9 0 221 15 6108551 (17 caracteres)
    private String telefono;

    @Version
    @Setter(AccessLevel.NONE) //impide que este campo sea seteable
    private Long version;

}
