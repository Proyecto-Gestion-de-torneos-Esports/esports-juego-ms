package com.juego.microservicio_juego.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JuegoNotFoundException.class)
    public ResponseEntity<?> manejoJuegoNoEncontrado(JuegoNotFoundException e){
        HashMap<String, Object> error = new HashMap<>();
        error.put("Estado",404);
        error.put("Mensaje",e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PlataformaNotFoundException.class)
    public ResponseEntity<?> manejoPlataformaNoEncontrada(PlataformaNotFoundException e){
        HashMap<String, Object> error = new HashMap<>();
        error.put("Estado",404);
        error.put("Mensaje",e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejoValidaciones (MethodArgumentNotValidException e){
        HashMap<Object, String> errores = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error-> errores.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manejoGeneral(Exception e){
        HashMap<Object, Object> error = new HashMap<>();

        error.put("estado",500);
        error.put("mensaje","Error interno en el servidor");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }




}
