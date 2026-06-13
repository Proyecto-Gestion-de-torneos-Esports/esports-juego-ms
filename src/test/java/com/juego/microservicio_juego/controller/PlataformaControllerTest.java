package com.juego.microservicio_juego.controller;

import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.service.PlataformaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlataformaController.class)
public class PlataformaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PlataformaService plataformaService;

    private Plataforma plat;

    @BeforeEach
    void setUp(){
        plat = new Plataforma();
        plat.setIdPlataforma(1L);
        plat.setNombrePlataforma("PC");
    }

    @Test
    void testObtenerTodo() throws Exception{

        when(plataformaService.obtenerPlataformas()).thenReturn(List.of(plat));

        mockMvc.perform(get("/api/plataforma"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPlataforma").value(1L))
                .andExpect(jsonPath("$[0].nombrePlataforma").value("PC"));

        List<Plataforma> plataformas = plataformaService.obtenerPlataformas();
        assertNotNull(plataformas);
        assertEquals(1, plataformas.size());
    }

    @Test
    void testBuscarPorId() throws Exception{
        when(plataformaService.obtenerPorId(1L)).thenReturn(plat);

        mockMvc.perform(get("/api/plataforma/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPlataforma").value(1))
                .andExpect(jsonPath("$.nombrePlataforma").value("PC"));

        verify(plataformaService).obtenerPorId(1L);
    }

    @Test
    void testAgregarPlataforma() throws Exception{
        when(plataformaService.agregarPlataforma(any(Plataforma.class))).thenReturn(plat);

        mockMvc.perform(post("/api/plataforma")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombrePlataforma":"PC"
                                }
                        """))
                .andExpect(status().isCreated());

        verify(plataformaService).agregarPlataforma(any(Plataforma.class));
    }

    @Test
    void testEliminarPlataforma() throws Exception{
        when(plataformaService.obtenerPorId(1L)).thenReturn(plat);

        mockMvc.perform(delete("/api/plataforma/1"))
                .andExpect(status().isNoContent());

        verify(plataformaService, times(1)).eliminarPlataforma(1L);
    }

}
