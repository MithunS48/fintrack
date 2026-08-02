package com.fintrack.fintrack.dto.dashboard;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DashboardResponse {

    private BigDecimal totalExpense;
    private BigDecimal totalIncome;
    private BigDecimal balance;
}
