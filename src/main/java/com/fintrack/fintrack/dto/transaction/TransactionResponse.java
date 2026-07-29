package com.fintrack.fintrack.dto.transaction;

import com.fintrack.fintrack.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {
    private Long id;

    private BigDecimal amount;

    private String description;

    private LocalDateTime transactionDateTime;

    private String categoryName;


    private TransactionType categoryType;



}
