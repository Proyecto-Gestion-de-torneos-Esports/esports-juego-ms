package com.juego.microservicio_juego.service;

import com.juego.microservicio_juego.exception.PlataformaNotFoundException;
import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.repository.PlataformaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PlataformaService {

    private final PlataformaRepository plataformaRepository;

    public List<Plataforma> obtenerPlataformas(){
        return plataformaRepository.findAll();
    }

    @Transactional
    public Plataforma agregarPlataforma(Plataforma plataforma){
        plataformaRepository.save(plataforma);
        return plataforma;
    }

    public Plataforma obtenerPorId(Long id){
        Optional<Plataforma> plataforma = plataformaRepository.findById(id);

        if(plataforma.isPresent()){
            log.info("Plataforma con id {} encontrada con exito!",id);
            return plataforma.orElseThrow();
        }
        log.warn("Plataforma con id {} no encontrada",id);
        throw new PlataformaNotFoundException("Plataforma con id "+id+" no encontrada");
    }

    @Transactional
    public void eliminarPlataforma(Long id) {
        Optional<Plataforma> plataforma = plataformaRepository.findById(id);

        if (plataforma.isPresent()) {
            log.info("Plataforma con id {} encontrada y eliminada con exito",id);
            plataformaRepository.deleteById(id);
            return;
        }
        log.warn("Error al eliminar, plataforma con id {} no encontrada",id);
        throw new PlataformaNotFoundException("Plataforma con id " + id + " no encontrada");
    }


}
