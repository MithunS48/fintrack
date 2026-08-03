package com.fintrack.fintrack.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter@Getter
public class YearlyReportResponse {

    private BigDecimal balance;
    private BigDecimal totalExpense;
    private BigDecimal totalIncome;
}
