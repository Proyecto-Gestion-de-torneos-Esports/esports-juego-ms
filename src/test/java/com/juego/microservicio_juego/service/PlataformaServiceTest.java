package com.juego.microservicio_juego.service;

import com.juego.microservicio_juego.exception.PlataformaNotFoundException;
import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.repository.PlataformaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlataformaServiceTest {

    @Mock
    private PlataformaRepository plataformaRepository;

    @InjectMocks
    private PlataformaService plataformaService;

    private Plataforma plat;

    @BeforeEach
    void setUp(){
        plat = new Plataforma();
        plat.setIdPlataforma(1L);
        plat.setNombrePlataforma("PC");
    }

    @Test
    void testObtenerPlataformas(){
        when(plataformaRepository.findAll()).thenReturn(List.of(plat));

        List<Plataforma> plataformas = plataformaService.obtenerPlataformas();

        assertNotNull(plataformas);
        assertEquals(1, plataformas.size());
    }

    @Test
    void testBuscarPorId(){
        Long id = 1L;
        when(plataformaRepository.findById(id)).thenReturn(Optional.of(plat));

        Plataforma plataforma = plataformaService.obtenerPorId(id);

        assertNotNull(plataforma);
        assertEquals(plat.getIdPlataforma(), plataforma.getIdPlataforma());
        assertEquals(plat.getNombrePlataforma(), plataforma.getNombrePlataforma());

        verify(plataformaRepository).findById(id);
    }

    @Test
    void testBuscarPorIdNoEncontrado(){
        when(plataformaRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(
                PlataformaNotFoundException.class,
                () -> plataformaService.obtenerPorId(2L));

        verify(plataformaRepository).findById(2L);
    }
    @Test
    void testAgregarPlataforma(){
        when(plataformaRepository.save(any(Plataforma.class))).thenReturn(plat);

        Plataforma plataforma = plataformaService.agregarPlataforma(plat);

        assertNotNull(plataforma);
        assertEquals(plat.getIdPlataforma(), plataforma.getIdPlataforma());
        assertEquals(plat.getNombrePlataforma(), plataforma.getNombrePlataforma());

        verify(plataformaRepository).save(any(Plataforma.class));
    }

    @Test
    void testEliminarPlataforma(){
        Long id = 1L;

        when(plataformaRepository.findById(id)).thenReturn(Optional.of(plat));

        plataformaService.eliminarPlataforma(id);

        verify(plataformaRepository).findById(id);
        verify(plataformaRepository).deleteById(id);
    }
}
