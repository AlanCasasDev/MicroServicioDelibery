package com.envios.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    // Cuando @Valid falla, Spring lanza MethodArgumentNotValidException.
    // Usamos Map porque pueden fallar varios campos a la vez:
    // { "nombre": "El nombre no puede estar vacío", "precio": "Debe ser mayor que cero" }
    //
    // Versión simplificada (solo devuelve el primer error):
    // String mensaje = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }


    //Excepciones Genericas
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarArgumentoInvalido(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("El argumento enviado no es válido.");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> manejarConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Uno o más valores enviados no cumplen con las validaciones requeridas.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> manejarIntegridadDatos(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Violacion de integridad de datos: " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<String> manejarBloqueoOptimista(OptimisticLockingFailureException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Conflicto de actualizacion concurrente: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarExcepcionGenerica(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocurrio un error inesperado: " + ex.getMessage());
    }



    //Excepciones Personalizadas
    @ExceptionHandler(TransicionEstadoException.class)
    public ResponseEntity<String> manejarTransicionEstado(TransicionEstadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(EstadoEnvioInvalidoException.class)
    public ResponseEntity<String> manejarEstadoEnvioInvalido(EstadoEnvioInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(EnvioNoEncontradoException.class)
    public ResponseEntity<String> manejarEnvioNoEncontrado(EnvioNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<String> manejarClienteNoEncontrado(ClienteNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ClienteApiNoDisponibleException.class)
    public ResponseEntity<String> manejarClienteNoDisponible(ClienteApiNoDisponibleException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ClienteApiException.class)
    public ResponseEntity<String> manejarClienteApi(ClienteApiException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ClienteApiTimeOutException.class)
    public ResponseEntity<String> manejarClienteApiTimeOut(ClienteApiTimeOutException ex) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ClienteApiComunicacionException.class)
    public ResponseEntity<String> manejarClienteApiComunicacion(ClienteApiComunicacionException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ex.getMessage());
    }


}
