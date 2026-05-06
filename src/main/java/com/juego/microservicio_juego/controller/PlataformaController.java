package com.juego.microservicio_juego.controller;

import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.service.PlataformaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/plataforma")
public class PlataformaController {

    private final PlataformaService plataformaService;

    @GetMapping
    public List<Plataforma> obtenerTodas(){
        return plataformaService.obtenerPlataformas();
    }

    @PostMapping
    public Plataforma agregarPlataforma(@RequestBody Plataforma plataforma){
        return plataformaService.agregarPlataforma(plataforma);
    }

    @DeleteMapping("/{id}")
    public void eliminarPlataforma(@PathVariable Long id){
        plataformaService.eliminarPlataforma(id);
    }


}
