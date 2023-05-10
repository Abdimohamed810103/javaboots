package com.javaboots.javaboots.advice;

import com.javaboots.javaboots.exceptions.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> invalidException(MethodArgumentNotValidException ex){
        Map<String,String> invalidEx = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            invalidEx.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return  invalidEx;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public Map<String,String> invalidId(ServiceException ex){
        Map<String,String> invalidEx = new HashMap<>();
        invalidEx.put("errorMassage", ex.getMessage());
        return invalidEx;
    }
}
