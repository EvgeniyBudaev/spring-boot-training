package com.aggregationjpa.exception;

import com.aggregationjpa.entity.ErrorEntity;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.util.InternalException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorEntity handleInternalServerException(InternalServerException e) {
        return error(e);
    }

    @ExceptionHandler(InternalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorEntity handleInternalServerException(InternalException e) {
        return error(e, "internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorEntity handleInternalServerException(PSQLException e) {
        return error(e, "database error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity handleBadRequestException(BadRequestException e) {
        return error(e);
    }

    @ExceptionHandler(exception = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity handleBadRequestException(Exception e) {
        return error(e, "bad request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String paramName = e.getName();
        e.getValue();
        String value = e.getValue().toString();
        e.getRequiredType();
        String requiredType = e.getRequiredType().getSimpleName();
        ErrorEntity error = new ErrorEntity();
        error.setStatusCode( HttpStatus.BAD_REQUEST.value());
        error.setServiceName("aggregation-jpa-service");
        error.setSuccess(false);
        error.setProdMessage("invalid parameter type");
        error.setDevMessage("parameter '" + paramName + "' with value '" + value +
                "' cannot be converted to " + requiredType);
        error.setErrorTime(Instant.now());
        return error;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorEntity handleNotFoundException(NotFoundException e) {
        return error(e);
    }

    @ExceptionHandler(exception = {
            EntityNotFoundException.class, // for JPA
            EmptyResultDataAccessException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorEntity handleNotFoundException(Exception e) {
        return error(e, "not found", HttpStatus.NOT_FOUND);
    }

    private ErrorEntity error(ApiException e) {
        ErrorEntity error = new ErrorEntity();
        error.setStatusCode(e.getStatusCode().value());
        error.setServiceName("aggregation-jpa-service");
        error.setSuccess(false);
        error.setDevMessage(e.getDevMessage());
        error.setProdMessage(e.getProdMessage());
        error.setErrorTime(Instant.now());
        return error;
    }

    private ErrorEntity error(Exception e, String prodMessage, HttpStatus code) {
        ErrorEntity error = new ErrorEntity();
        error.setStatusCode(code.value());
        error.setServiceName("aggregation-jpa-service");
        error.setSuccess(false);
        error.setDevMessage(e.getMessage());
        error.setProdMessage(prodMessage);
        error.setErrorTime(Instant.now());
        return error;
    }
}
