package com.juego.microservicio_juego.service;

import com.juego.microservicio_juego.exception.PlataformaNotFoundException;
import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.repository.PlataformaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlataformaService {

    private final PlataformaRepository plataformaRepository;

    public List<Plataforma> obtenerPlataformas(){
        return plataformaRepository.findAll();
    }

    public Plataforma agregarPlataforma(Plataforma plataforma){
        plataformaRepository.save(plataforma);
        return plataforma;
    }

    public Optional<Plataforma> obtenerPorId(Long id){
        Optional<Plataforma> plataforma = plataformaRepository.findById(id);

        if(plataforma.isPresent()){
            return plataforma;
        }
        throw new PlataformaNotFoundException("Plataforma con id "+id+" no encontrada");
    }

    public void eliminarPlataforma(Long id) {
        Optional<Plataforma> plataforma = plataformaRepository.findById(id);

        if (plataforma.isPresent()) {
            plataformaRepository.deleteById(id);
        }
        throw new PlataformaNotFoundException("Plataforma con id " + id + " no encontrada");
    }


}
