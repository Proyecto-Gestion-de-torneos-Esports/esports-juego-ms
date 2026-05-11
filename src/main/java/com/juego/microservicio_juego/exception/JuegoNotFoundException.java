package com.juego.microservicio_juego.exception;


public class JuegoNotFoundException extends RuntimeException{

    public JuegoNotFoundException(String mensaje){
        super(mensaje);
    }
}
