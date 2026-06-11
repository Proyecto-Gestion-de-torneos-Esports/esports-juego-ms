package com.juego.microservicio_juego;

import com.juego.microservicio_juego.client.AuditoriaClient;
import com.juego.microservicio_juego.dto.JuegoRequestDTO;
import com.juego.microservicio_juego.dto.JuegoResponseDTO;
import com.juego.microservicio_juego.exception.JuegoNotFoundException;
import com.juego.microservicio_juego.model.Juego;
import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.repository.JuegoRepository;
import com.juego.microservicio_juego.repository.PlataformaRepository;
import com.juego.microservicio_juego.service.JuegoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JuegoServiceTest {

    @Mock
    private JuegoRepository juegoRepository;

    @Mock
    private PlataformaRepository plataformaRepository;

    @Mock
    private AuditoriaClient auditoriaClient;

    @InjectMocks
    private JuegoService juegoService;

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("Busqueda de juegos por id")
    void testObtenerTodos() {
        Plataforma plat = new Plataforma(null, "PC");
        Juego juego = new Juego(null, "Counter-Strike", "FPS", "Valve", Set.of(plat));

        when(juegoRepository.findAll()).thenReturn(List.of(juego));

        List<JuegoResponseDTO> juegos = juegoService.obtenerJuegos();

        assertNotNull(juegos);
        assertEquals(1,juegos.size());
    }

    @Test
    void testBuscarPorId(){
        Long id = 1L;
        Plataforma plat = new Plataforma(1L, "PC");
        Juego juego = new Juego(1L, "Counter-Strike", "FPS", "Valve", Set.of(plat));

        when(juegoRepository.findById(id)).thenReturn(Optional.of(juego));

        JuegoResponseDTO juegos = juegoService.obtenerJuegoPorId(id);

        assertNotNull(juegos);
        assertEquals(juego.getIdJuego(), juegos.getId());
        assertEquals(juego.getNombre(), juegos.getNombre());
        assertEquals(juego.getGenero(), juegos.getGenero());
        assertEquals(juego.getDistribuidor(), juegos.getDistribuidor());
        assertEquals(Set.of(plat.getIdPlataforma()), juegos.getPlataformaId());

        verify(juegoRepository).findById(id);
    }

    @Test
    void testFindByIdNotFound(){
        when(juegoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(
                JuegoNotFoundException.class,
                () -> juegoService.obtenerJuegoPorId(99L));

        verify(juegoRepository).findById(99L);
    }

    @Test
    void testAgregarJuego(){
        Plataforma plat = new Plataforma(1L, "PC");
        Juego juego = new Juego(1L, "Counter-Strike", "FPS", "Valve", Set.of(plat));

        JuegoRequestDTO juegoRequestDTO = new JuegoRequestDTO();
        juegoRequestDTO.setNombre(juego.getNombre());
        juegoRequestDTO.setGenero(juego.getGenero());
        juegoRequestDTO.setDistribuidor(juego.getDistribuidor());
        juegoRequestDTO.setIdPlataformas(Set.of(plat.getIdPlataforma()));

        when(plataformaRepository.findById(1L)).thenReturn(Optional.of(plat));

        when(juegoRepository.save(any(Juego.class))).thenReturn(juego);

        JuegoResponseDTO juegos = juegoService.agregarJuego(juegoRequestDTO);

        assertNotNull(juegos);
        assertEquals(juego.getIdJuego(), juegos.getId());
        assertEquals(juego.getNombre(), juegos.getNombre());
        assertEquals(juego.getGenero(), juegos.getGenero());
        assertEquals(juego.getDistribuidor(), juegos.getDistribuidor());
        assertEquals(Set.of(plat.getIdPlataforma()), juegos.getPlataformaId());

        verify(plataformaRepository).findById(1L);
        verify(juegoRepository).save(any(Juego.class));
        verify(auditoriaClient).generarAuditoria(any());
    }

    @Test
    void testModificarJuego(){
        Long id = 1L;
        Plataforma plat = new Plataforma(1L, "PC");
        Juego juego = new Juego(1L, "Counter-Strike", "FPS", "Valve", Set.of(plat));
        Juego juegoModificado = new Juego(1L, "Counter-Strike", "Shooter", "Valve", Set.of(plat));
        JuegoRequestDTO juegoRequestDTO = new JuegoRequestDTO();
        juegoRequestDTO.setNombre(juego.getNombre());
        juegoRequestDTO.setGenero("Shooter");
        juegoRequestDTO.setDistribuidor(juego.getDistribuidor());
        juegoRequestDTO.setIdPlataformas(Set.of(plat.getIdPlataforma()));

        when(juegoRepository.findById(id)).thenReturn(Optional.of(juego));

        when(plataformaRepository.findById(plat.getIdPlataforma())).thenReturn(Optional.of(plat));

        when(juegoRepository.save(any(Juego.class))).thenReturn(juegoModificado);

        JuegoResponseDTO juegos = juegoService.modificarJuego(id,juegoRequestDTO);

        assertEquals("Shooter",juegoModificado.getGenero());

        verify(plataformaRepository).findById(plat.getIdPlataforma());
        verify(juegoRepository).findById(id);
        verify(juegoRepository).save(juego);
    }

    @Test
    void testEliminarJuego(){
        Long id = 1L;
        Plataforma plat = new Plataforma(1L, "PC");
        Juego juego = new Juego(1L, "Counter-Strike", "FPS", "Valve", Set.of(plat));

        when(juegoRepository.findById(id)).thenReturn(Optional.of(juego));

        juegoService.eliminarJuego(id);

        verify(juegoRepository).findById(id);
        verify(juegoRepository).deleteById(id);
    }


}
