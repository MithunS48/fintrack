package com.fintrack.fintrack.exception;

import com.fintrack.fintrack.dto.exception.ExceptionResponse;
import com.fintrack.fintrack.dto.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalException {

    public ResponseEntity<ExceptionResponse> buildException(HttpStatus status, String message, HttpServletRequest request)
    {
        ExceptionResponse response=new ExceptionResponse();
        response.setLocalDateTime(LocalDateTime.now());
        response.setStatus(status.value());
        response.setError(status.getReasonPhrase());
        response.setMessage(message);
        response.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> userAlreadyExist(UserAlreadyExistsException ex, HttpServletRequest request)
    {

        return buildException(HttpStatus.CONFLICT,ex.getMessage(),request);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> categoryAlreadyExist(CategoryAlreadyExistsException ex, HttpServletRequest request)
    {
        return buildException(HttpStatus.CONFLICT,ex.getMessage(),request);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> categoryNotFound(CategoryNotFoundException ex, HttpServletRequest request)
    {
        return buildException(HttpStatus.NOT_FOUND,ex.getMessage(),request);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ExceptionResponse> transactionNotFound(TransactionNotFoundException ex, HttpServletRequest request)
    {
        return buildException(HttpStatus.NOT_FOUND,ex.getMessage(),request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> userNotFound(UserNotFoundException ex, HttpServletRequest request)
    {
        return buildException(HttpStatus.NOT_FOUND,ex.getMessage(),request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationException> validException(MethodArgumentNotValidException ex,HttpServletRequest request)
    {
        Map<String,String> map=new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error->{map.put(error.getField(), error.getDefaultMessage());});

        ValidationException response=new ValidationException();
        response.setLocalDateTime(LocalDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage("Validation failed");
        response.setPath(request.getRequestURI());
        response.setErrors(map);

        return ResponseEntity.badRequest().body(response);
    }


}
