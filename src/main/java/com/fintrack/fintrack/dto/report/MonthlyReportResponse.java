package com.fintrack.fintrack.dto.report;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MonthlyReportResponse {


    private BigDecimal balance;
    private BigDecimal totalExpense;
    private BigDecimal totalIncome;
}
