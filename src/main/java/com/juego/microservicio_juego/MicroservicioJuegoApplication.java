package com.juego.microservicio_juego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableFeignClients
@EnableMethodSecurity
public class MicroservicioJuegoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioJuegoApplication.class, args);
	}

}
