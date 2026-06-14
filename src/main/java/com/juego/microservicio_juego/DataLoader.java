package com.juego.microservicio_juego;

import com.juego.microservicio_juego.model.Juego;
import com.juego.microservicio_juego.model.Plataforma;
import com.juego.microservicio_juego.repository.JuegoRepository;
import com.juego.microservicio_juego.repository.PlataformaRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Profile("dev")
@Configuration
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JuegoRepository juegoRepository;

    @Autowired
    private PlataformaRepository plataformaRepository;



    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();
        Random random = new Random();

        Set<Plataforma> plataformas = new HashSet<>();
        //Plataforma
        for(int i = 0; i<3; i++){
            Plataforma plataforma = new Plataforma();
            plataforma.setIdPlataforma((long)(i+1));
            plataforma.setNombrePlataforma(faker.videoGame().platform());
            plataformas.add(plataforma);
            plataformaRepository.save(plataforma);
        }

        //Juego
        for(int i = 0; i<3; i++){
            Juego juego = new Juego();
            juego.setIdJuego((long)(i+1));
            juego.setNombre(faker.videoGame().title());
            juego.setGenero(faker.videoGame().genre());
            juego.setDistribuidor(faker.company().name());
            juego.setPlataformas(plataformas);
            juegoRepository.save(juego);
        }

    }

}
