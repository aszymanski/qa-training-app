/* (C)2023 */
package com.springbootbooks.api.advice;

import com.springbootbooks.api.exceptions.IntegrityConstraintViolationException;
import com.springbootbooks.api.exceptions.ObjectNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        MethodArgumentNotValidException.class,
    })
    public Map<String, String> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(
                        error -> {
                            errorMap.put(error.getField(), error.getDefaultMessage());
                        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
        ObjectNotFoundException.class,
    })
    public Map<String, String> handleObjectNotFoundException(ObjectNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        MethodArgumentTypeMismatchException.class,
    })
    public Map<String, String> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        String name = exception.getName();
        String type = exception.getRequiredType().getSimpleName();
        Object value = exception.getValue();
        String message =
                String.format("'%s' should be a valid: '%s' but is: '%s'", name, type, value);
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", message);
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        IntegrityConstraintViolationException.class,
    })
    public Map<String, String> handleIntegrityConstraintViolationException(
            IntegrityConstraintViolationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        ConstraintViolationException.class,
    })
    public Map<String, String> handleConstraintViolationException(
            ConstraintViolationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            errorMap.put("error", violation.getMessage());
        }
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        IllegalStateException.class,
    })
    public Map<String, String> handleIllegalStateException(IllegalStateException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        DataIntegrityViolationException.class,
    })
    public Map<String, String> handlePatchNotAllowed(DataIntegrityViolationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        HttpMessageNotReadableException.class,
    })
    public Map<String, String> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", exception.getMessage());
        return errorMap;
    }
}
