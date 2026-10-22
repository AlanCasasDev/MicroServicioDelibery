package com.envios.dto;

import com.envios.model.enum_envio.EnumEstadoEnvio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class EnvioDTOResponse {

    @NotNull(message = "El id del envio es obligatorio")
    @Positive(message = "El id debe ser obligatoriamente positivo.")
    private Integer id;

    @PastOrPresent(message = "La fecha del envio debe ser pasada o presente.")
    private LocalDateTime fechaDeCreacion;

    @NotBlank(message = "El envio debe tener si o si una descripcion.")
    private String descripcion;

    @NotNull(message = "El envio debe tener si o si un estado asociado.")
    private EnumEstadoEnvio estado;

    @NotNull(message = "El envio tiene que tener si o si asociado el id del cliente. No puede estar vacio.")
    @Positive(message = "El id de cliente debe ser positivo")
    private Integer idCliente;


}
