package dev.yrmg.userwebapi.userwebapi.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.validation.FieldError;

import dev.yrmg.userwebapi.userwebapi.shared.Response;

@ControllerAdvice
public class ExceptionHandlerApi extends ResponseEntityExceptionHandler{

    public ExceptionHandlerApi(){

    }
    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<Object> handleValidationEntity(ValidationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new Response<Object>(false, "Something went wrong", Arrays.asList(ex.getMessage())));
        
    }

    
}
