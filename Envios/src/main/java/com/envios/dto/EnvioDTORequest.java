package com.envios.dto;

import com.envios.model.enum_envio.EnumEstadoEnvio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class EnvioDTORequest {

    //La fecha de creacion debe ser automatica a la hora crearse
//    @PastOrPresent(message = "La fecha de envio debe ser presente o pasada")
//    private LocalDateTime fechaDeCreacion;

    @NotBlank(message = "El envio debe tener si o si una descripcion.")
    private String descripcion;

    @NotNull(message = "El envio debe tener asociado un estado obligatorio.")
    private EnumEstadoEnvio estado; //TODO hacer que la peticion de estado sea String y que la logica lo acomode al enum

    @NotNull(message = "El envio tiene que tener si o si asociado el id del cliente. No puede estar vacio.")
    @Positive(message = "El id de cliente debe ser positivo")
    private Integer idCliente;

}
