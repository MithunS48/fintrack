package com.fintrack.fintrack.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {


    private LocalDateTime localDateTime;
    private int status;
    private String error;
    private String message;
    private String path;
}
