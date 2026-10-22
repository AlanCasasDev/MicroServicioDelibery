package com.clientes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

//Datos responde esta API al Client
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClienteDTOResponse {

    //id, dni, nombre, apellido, direccion y num de telefono
    @Positive(message = "El id es estrictamente positivo")
    @NotNull(message = "El id es obligatorio.")
    private Integer id;

    @NotBlank(message = "El dni no puede estar en blanco ni vacio.")
    @Pattern(
            regexp = "^\\d{8,9}$",
            message = "El DNI debe contener entre 8 y 9 dígitos numéricos"
    )
    private String dni;

    @NotBlank(message = "El nombre no puede quedar vacio. Es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido no puede quedar vacio. Es obligatorio")
    private String apellido;

    @NotBlank(message = "La direccion no puede quedar vacio. Es obligatoria")
    private String direccion;

    @NotBlank (message = "El numero de telefono no puede quedar vacio. Es obligatorio")
    @Pattern(
            regexp = "^\\+?[0-9]{8,15}$",
            message = "Número de teléfono inválido"
    ) //Permite formato universal +5492216108551 o 2216108551
    private String telefono;
}
