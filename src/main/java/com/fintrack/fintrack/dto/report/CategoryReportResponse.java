package com.fintrack.fintrack.dto.report;


import com.fintrack.fintrack.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CategoryReportResponse {

    private String categoryName;
    private TransactionType categoryType;
    private BigDecimal totalAmount;
}
