package com.juego.microservicio_juego.exception;

public class PlataformaNotFoundException extends RuntimeException{
    public PlataformaNotFoundException(String mensaje){
        super(mensaje);
    }
}
