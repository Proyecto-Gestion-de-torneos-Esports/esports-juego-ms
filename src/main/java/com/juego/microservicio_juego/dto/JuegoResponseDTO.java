package com.juego.microservicio_juego.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuegoResponseDTO extends RepresentationModel<JuegoResponseDTO> {

    private Long id;
    private String nombre;
    private String genero;
    private String distribuidor;
    private Set<Long> plataformaId;

}
