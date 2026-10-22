package com.envios.model;

import com.envios.model.enum_envio.EnumEstadoEnvio;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaDeCreacion;

    @Column(name = "descripcion", nullable = false, updatable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EnumEstadoEnvio estado;

    @Column(name = "idCliente", nullable = false, updatable = false)
    private Integer idCliente;

    @Version
    @Setter(AccessLevel.NONE)
    private Integer version;

}
