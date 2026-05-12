package com.juego.microservicio_juego.controller;

import com.juego.microservicio_juego.dto.JuegoRequestDTO;
import com.juego.microservicio_juego.dto.JuegoResponseDTO;
import com.juego.microservicio_juego.service.JuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/juego")
public class JuegoController {

    private final JuegoService juegoService;

    @GetMapping
    public ResponseEntity<List<JuegoResponseDTO>> listarJuego(){
        return ResponseEntity.ok(juegoService.obtenerJuegos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(juegoService.obtenerJuegoPorId(id));
    }

    @PostMapping
    public ResponseEntity<JuegoResponseDTO> guardarJuego(@Valid @RequestBody JuegoRequestDTO juego){
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoService.agregarJuego(juego));
    }

    @PutMapping("{id}")
    public ResponseEntity<JuegoResponseDTO> modificarJuego(@PathVariable Long id, @Valid @RequestBody JuegoRequestDTO juego){
        return ResponseEntity.ok(juegoService.modificarJuego(id, juego));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarJuego(@PathVariable Long id){
        juegoService.eliminarJuego(id);
        return ResponseEntity.noContent().build();
    }
}
