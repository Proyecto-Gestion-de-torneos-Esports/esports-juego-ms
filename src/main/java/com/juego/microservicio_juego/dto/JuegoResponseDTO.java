package com.juego.microservicio_juego.dto;


import com.juego.microservicio_juego.model.Plataforma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuegoResponseDTO {

    private Long id;
    private String nombre;
    private String genero;
    private String distribuidor;
    private Set<Long> plataformaId;

}
