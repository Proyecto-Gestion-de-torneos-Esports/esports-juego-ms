package com.juego.microservicio_juego.controller;

import com.juego.microservicio_juego.assemblers.PlataformaModelAssembler;
import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.service.PlataformaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/plataforma")
@Tag(name = "Plataforma", description = "Operaciones con las plataformas")
public class PlataformaController {

    private final PlataformaModelAssembler plataformaAssembler;
    private final PlataformaService plataformaService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('ARBITRO') or hasRole('COACH') or hasRole('JUGADOR')")
    @GetMapping
    @Operation(summary = "Listar plataformas", description = "Listado de plataformas disponibles")
    public ResponseEntity<?> obtenerTodas(){
        List<EntityModel<Plataforma>> plataformas = plataformaService.obtenerPlataformas()
                .stream().map(plataformaAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Plataforma>> collectionModel =  CollectionModel.of(plataformas, linkTo(methodOn(PlataformaController.class).obtenerTodas()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ARBITRO') or hasRole('COACH') or hasRole('JUGADOR')")
    @GetMapping("/{id}")
    @Operation(summary = "Busqueda de plataformas por su ID", description = "Consulta de una plataforma en especifico")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        Plataforma plataforma = plataformaService.obtenerPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(plataformaAssembler.toModel(plataforma));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Agregar plataforma", description = "Registro de plataforma validando sus datos de entrada")
    public ResponseEntity<?> agregarPlataforma(@Valid @RequestBody Plataforma plataforma){
        Plataforma plataforma1 = plataformaService.agregarPlataforma(plataforma);
        return ResponseEntity.status(HttpStatus.CREATED).body(plataformaAssembler.toModel(plataforma1));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar plataforma", description = "Eliminacion de plataforma por medio de su ID")
    public ResponseEntity<?> eliminarPlataforma(@PathVariable Long id){
        plataformaService.eliminarPlataforma(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
