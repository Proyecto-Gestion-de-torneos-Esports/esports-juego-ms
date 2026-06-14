package com.juego.microservicio_juego.controller;

import com.juego.microservicio_juego.assemblers.JuegoModelAssembler;
import com.juego.microservicio_juego.dto.JuegoRequestDTO;
import com.juego.microservicio_juego.dto.JuegoResponseDTO;
import com.juego.microservicio_juego.model.Juego;
import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.service.JuegoService;
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
@RequestMapping("/api/juego")
@Tag(name = "Juego", description = "Operaciones relacionadas con los juegos")
public class JuegoController {

    private final JuegoService juegoService;
    private final JuegoModelAssembler juegoAssembler;

    @PreAuthorize("hasRole('ADMIN') or hasRole('ARBITRO') or hasRole('COACH') or hasRole('JUGADOR')")
    @GetMapping
    @Operation(summary = "Listar juegos", description = "Consulta de juegos disponibles")
    public ResponseEntity<?> listarJuego(){
        List<JuegoResponseDTO> juegos = juegoService.obtenerJuegos()
                .stream().map(juegoAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<JuegoResponseDTO> collectionModel =  CollectionModel.of(juegos, linkTo(methodOn(JuegoController.class).listarJuego()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ARBITRO') or hasRole('COACH') or hasRole('JUGADOR')")
    @GetMapping("/{id}")
    @Operation(summary = "Busqueda de juegos por su ID", description = "Consulta de un juego en especifico")
    public ResponseEntity<JuegoResponseDTO> buscarPorId(@PathVariable Long id){
        JuegoResponseDTO juego = juegoService.obtenerJuegoPorId(id);
        return ResponseEntity.ok(juegoAssembler.toModel(juego));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Agregar un nuevo juego", description = "Registro de un juego validando sus datos de entrada")
    public ResponseEntity<JuegoResponseDTO> guardarJuego(@Valid @RequestBody JuegoRequestDTO juego){
        JuegoResponseDTO dto = juegoService.agregarJuego(juego);
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoAssembler.toModel(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    @Operation(summary = "Modificar un juego", description = "Modificacion de un juego usando su ID")
    public ResponseEntity<JuegoResponseDTO> modificarJuego(@PathVariable Long id, @Valid @RequestBody JuegoRequestDTO juego){
        JuegoResponseDTO dto =juegoService.modificarJuego(id, juego);
        return ResponseEntity.ok(juegoAssembler.toModel(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar un juego", description = "Eliminar un juego por medio de un ID")
    public ResponseEntity<?> eliminarJuego(@PathVariable Long id){
        juegoService.eliminarJuego(id);
        return ResponseEntity.noContent().build();
    }
}
