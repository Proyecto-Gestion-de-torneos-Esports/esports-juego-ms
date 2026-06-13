package com.juego.microservicio_juego.controller;

import com.juego.microservicio_juego.client.AuditoriaClient;
import com.juego.microservicio_juego.dto.JuegoRequestDTO;
import com.juego.microservicio_juego.dto.JuegoResponseDTO;
import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.service.JuegoService;
import com.juego.microservicio_juego.service.PlataformaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(JuegoController.class)
public class JuegoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private JuegoService juegoService;

    @Mock
    private PlataformaService plataformaService;

    @Mock
    private AuditoriaClient auditoriaClient;

    private Plataforma plat;
    private JuegoResponseDTO juego;

    @BeforeEach
    void setUp(){
        plat = new Plataforma();
        plat.setIdPlataforma(1L);
        plat.setNombrePlataforma("PC");

        juego = new JuegoResponseDTO();
        juego.setId(1L);
        juego.setNombre("Counter-Strike");
        juego.setGenero("FPS");
        juego.setDistribuidor("Valve");
        juego.setPlataformaId(Set.of(plat.getIdPlataforma()));
    }

    @Test
    void testListarJuegos() throws Exception{
        when(juegoService.obtenerJuegos()).thenReturn(List.of(juego));

        mockMvc.perform(get("/api/juego"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Counter-Strike"))
                .andExpect(jsonPath("$[0].genero").value("FPS"))
                .andExpect(jsonPath("$[0].distribuidor").value("Valve"))
                .andExpect(jsonPath("$[0].plataformaId[0]").value(1));

        List<JuegoResponseDTO> juegos = juegoService.obtenerJuegos();
        assertNotNull(juegos);
        assertEquals(1, juegos.size());
    }

    @Test
    void testBuscarPorId() throws Exception{
        when(juegoService.obtenerJuegoPorId(1L)).thenReturn(juego);

        mockMvc.perform(get("/api/juego/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Counter-Strike"))
                .andExpect(jsonPath("$.genero").value("FPS"))
                .andExpect(jsonPath("$.distribuidor").value("Valve"))
                .andExpect(jsonPath("$.plataformaId[0]").value(1));

        verify(juegoService).obtenerJuegoPorId(1L);
    }

    @Test
    void testGuardarJuego() throws Exception{
        when(juegoService.agregarJuego(any(JuegoRequestDTO.class))).thenReturn(juego);

        mockMvc.perform(post("/api/juego")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "nombre":"Counter-Strike",
                            "genero":"FPS",
                            "distribuidor":"Valve",
                            "idPlataformas": [1]
                        }
                        """))
                .andExpect(status().isCreated());

        verify(juegoService).agregarJuego(any(JuegoRequestDTO.class));
    }

    @Test
    void testModificarJuego() throws Exception{
        when(juegoService.modificarJuego(eq(1L), any(JuegoRequestDTO.class))).thenReturn(juego);

        mockMvc.perform(put("/api/juego/1", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "nombre":"Counter-Strike",
                            "genero":"FPS",
                            "distribuidor":"Valve",
                            "idPlataformas": [1]
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Counter-Strike"))
                .andExpect(jsonPath("$.genero").value("FPS"))
                .andExpect(jsonPath("$.distribuidor").value("Valve"));
    }

    @Test
    void testEliminarJuego() throws Exception{
        when(juegoService.obtenerJuegoPorId(1L)).thenReturn(juego);

        mockMvc.perform(delete("/api/juego/1"))
                .andExpect(status().isNoContent());

        verify(juegoService,times(1)).eliminarJuego(1L);



    }
}
