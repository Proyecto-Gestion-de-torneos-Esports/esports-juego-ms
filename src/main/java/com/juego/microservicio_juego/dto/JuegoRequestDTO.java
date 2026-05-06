package com.juego.microservicio_juego.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuegoRequestDTO {

    @NotBlank(message = "El nombre no puede ser vacio ni nulo")
    private String nombre;

    @NotBlank(message = "El genero no puede ser vacio ni nulo")
    private String genero;

    private String distribuidor;

    @NotNull(message = "La plataforma no puede ser vacio ni nulo")
    private Set<Long> idPlataformas;
}
