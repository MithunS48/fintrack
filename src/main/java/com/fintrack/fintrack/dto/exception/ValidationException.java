package com.fintrack.fintrack.dto.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ValidationException extends ExceptionResponse{

    private Map<String,String> errors;
}
