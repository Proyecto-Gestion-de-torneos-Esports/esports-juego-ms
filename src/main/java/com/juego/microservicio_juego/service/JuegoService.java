package com.juego.microservicio_juego.service;

import com.juego.microservicio_juego.client.AuditoriaClient;
import com.juego.microservicio_juego.dto.AuditoriaRequestDTO;
import com.juego.microservicio_juego.dto.JuegoRequestDTO;
import com.juego.microservicio_juego.dto.JuegoResponseDTO;
import com.juego.microservicio_juego.exception.JuegoNotFoundException;
import com.juego.microservicio_juego.exception.PlataformaNotFoundException;
import com.juego.microservicio_juego.model.Juego;
import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.repository.JuegoRepository;
import com.juego.microservicio_juego.repository.PlataformaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JuegoService {

    private final JuegoRepository juegoRepository;
    private final PlataformaRepository plataformaRepository;
    private final AuditoriaClient auditoriaClient;

    private JuegoResponseDTO mapToDTO(Juego juego){
        return new JuegoResponseDTO(
            juego.getIdJuego(),
            juego.getNombre(),
            juego.getGenero(),
            juego.getDistribuidor(),
            juego.getPlataformas().stream().map(Plataforma::getIdPlataforma).collect(Collectors.toSet())
        );
    }

    public List<JuegoResponseDTO> obtenerJuegos(){
        return juegoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public JuegoResponseDTO obtenerJuegoPorId(Long id){
        Optional<Juego> juego = juegoRepository.findById(id);

        if(juego.isPresent()){
            log.info("Juego con id {} encontrado con exito!",id);
            return juego.map(this::mapToDTO).orElseThrow();

        }
        log.warn("Juego con id {} no encontrado",id);
        throw new JuegoNotFoundException("Juego con id "+id+" no encontrado");
    }

    @Transactional
    public JuegoResponseDTO agregarJuego(JuegoRequestDTO dto){
        Set<Plataforma> idPlataforma = dto.getIdPlataformas().stream()
                .map(plataformaId -> plataformaRepository.findById(plataformaId)
                        .orElseThrow(()-> new PlataformaNotFoundException("Plataforma no encontrada: "+plataformaId)))
                .collect(Collectors.toSet());

        Juego juego = new Juego(null, dto.getNombre(), dto.getGenero(), dto.getDistribuidor(), idPlataforma);
        log.info("Juego agregado con exito!");
        generarAuditoria("Se agrego un juego");
        return mapToDTO(juegoRepository.save(juego));
    }

    @Transactional
    public JuegoResponseDTO modificarJuego(Long id, JuegoRequestDTO dto){
        Optional<Juego> juego = juegoRepository.findById(id);

        if(juego.isPresent()){
            return juego.map(existente -> {
                existente.setNombre(dto.getNombre());
                existente.setGenero(dto.getGenero());
                existente.setDistribuidor(dto.getDistribuidor());

                Set<Plataforma> idPlataforma = dto.getIdPlataformas().stream()
                        .map(plataformaId-> plataformaRepository.findById(plataformaId).
                                orElseThrow(() -> new PlataformaNotFoundException("Plataforma con id "+plataformaId+ " no encontrada")))
                        .collect(Collectors.toSet());

                existente.setPlataformas(idPlataforma);
                log.info("Juego modificado con exito!");
                generarAuditoria("Juego modificado");
                return mapToDTO(juegoRepository.save(existente));
            }).orElseThrow();
        }
        log.warn("Error al actualizar, juego con id {} no encontrado",id);
        throw new JuegoNotFoundException("Juego con id "+id+" no encontrado");

    }

    @Transactional
    public void eliminarJuego(Long id){
        Optional<Juego> juego = juegoRepository.findById(id);

        if(juego.isPresent()){
            juegoRepository.deleteById(id);
            log.info("Juego eliminado con exito!");
            generarAuditoria("Juego eliminado");
        }
        log.warn("Error al eliminar, juego con id {} no encontrado",id);
        throw new JuegoNotFoundException("Juego con id "+id+" no encontrado");
    }

    @Transactional
    public void generarAuditoria(String detalle){
        AuditoriaRequestDTO dto = new AuditoriaRequestDTO();
        LocalDate ahora = LocalDate.now();
        dto.setDetalle(detalle);
        dto.setFecha(ahora);
        auditoriaClient.generarAuditoria(dto);
        log.info("Auditoria generada con exito!");
    }


}
