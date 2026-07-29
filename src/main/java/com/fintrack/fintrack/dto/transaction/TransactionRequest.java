package com.fintrack.fintrack.dto.transaction;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionRequest {


    private BigDecimal amount;

    @NotBlank
    @Size(min=5,max=255)
    private String description;




    private Long userId;


    private Long categoryId;
}
