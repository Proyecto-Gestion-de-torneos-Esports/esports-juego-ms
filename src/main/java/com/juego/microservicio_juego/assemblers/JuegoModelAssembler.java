package com.juego.microservicio_juego.assemblers;

import com.juego.microservicio_juego.controller.JuegoController;
import com.juego.microservicio_juego.dto.JuegoResponseDTO;
import com.juego.microservicio_juego.model.Juego;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JuegoModelAssembler implements RepresentationModelAssembler<JuegoResponseDTO, JuegoResponseDTO> {

    @Override
    public JuegoResponseDTO toModel(JuegoResponseDTO juego){

        juego.add(linkTo(methodOn(JuegoController.class).listarJuego()).withRel("juegos"));
        juego.add(linkTo(methodOn(JuegoController.class).buscarPorId(juego.getId())).withSelfRel());
        return juego;
    }
}

