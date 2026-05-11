package com.juego.microservicio_juego.controller;

import com.juego.microservicio_juego.dto.JuegoRequestDTO;
import com.juego.microservicio_juego.dto.JuegoResponseDTO;
import com.juego.microservicio_juego.service.JuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/juego")
public class JuegoController {

    private final JuegoService juegoService;

    @GetMapping
    public List<JuegoResponseDTO> listarJuego(){
        return juegoService.obtenerJuegos();
    }

    @GetMapping("/{id}")
    public Optional<JuegoResponseDTO> buscarPorId(@PathVariable Long id){
        return juegoService.obtenerJuegoPorId(id);
    }

    @PostMapping
    public JuegoResponseDTO guardarJuego(@Valid @RequestBody JuegoRequestDTO juego){
        return juegoService.agregarJuego(juego);
    }

    @PutMapping("{id}")
    public Optional<JuegoResponseDTO> modificarJuego(@PathVariable Long id, @Valid @RequestBody JuegoRequestDTO juego){
        return juegoService.modificarJuego(id, juego);
    }

    @DeleteMapping("{id}")
    public void eliminarJuego(@PathVariable Long id){
        juegoService.eliminarJuego(id);
    }
}
