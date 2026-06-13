package com.juego.microservicio_juego.assemblers;

import com.juego.microservicio_juego.controller.PlataformaController;
import com.juego.microservicio_juego.model.Plataforma;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlataformaModelAssembler implements RepresentationModelAssembler<Plataforma, EntityModel<Plataforma>> {

    @Override
    public EntityModel<Plataforma> toModel(Plataforma plataforma){
        return EntityModel.of(plataforma,
                linkTo(methodOn(PlataformaController.class).obtenerTodas()).withRel("plataforma"),
                linkTo(methodOn(PlataformaController.class).buscarPorId(plataforma.getIdPlataforma())).withSelfRel());
    }

}
