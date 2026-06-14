package com.juego.microservicio_juego.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "juego")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 5)
    private Long idJuego;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String genero;

    @Column(length = 100)
    private String distribuidor;

    @ManyToMany
    @JoinTable(name = "juego_plataforma")
    private Set<Plataforma> plataformas;



}
