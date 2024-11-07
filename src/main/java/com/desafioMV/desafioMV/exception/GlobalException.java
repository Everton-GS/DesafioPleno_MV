package com.desafioMV.desafioMV.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalException {
    
        @ExceptionHandler(TimeoutException.class)
        public ResponseEntity<String> handleTimeException(TimeoutException timeout){
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(timeout.getMessage());
        }

        @ExceptionHandler(CustomInternalServerError.class)
        public ResponseEntity<String> handleInternoServerError(CustomInternalServerError internalServerError){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(internalServerError.getMessage());
        }

        @ExceptionHandler(CustomUserNotFoundException.class)
        public ResponseEntity<String> handleUserNotFoundException(CustomUserNotFoundException userNotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNotFoundException.getMessage());
        }
       
        @ExceptionHandler(CustomNotificacaoNotFoundException.class)
        public ResponseEntity<String> handlerNotificacaoNotFoundException(CustomNotificacaoNotFoundException customNotificacaoNotFoundException){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customNotificacaoNotFoundException.getMessage());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
