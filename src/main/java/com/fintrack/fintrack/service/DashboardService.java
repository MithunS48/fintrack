package com.fintrack.fintrack.service;


import com.fintrack.fintrack.dto.dashboard.DashboardResponse;
import com.fintrack.fintrack.entity.Transaction;
import com.fintrack.fintrack.entity.User;
import com.fintrack.fintrack.enums.TransactionType;
import com.fintrack.fintrack.exception.UserNotFoundException;
import com.fintrack.fintrack.repository.TransactionRepo;
import com.fintrack.fintrack.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;


    public DashboardResponse getDashboard(UserDetails userDetails)
    {
        User user=userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()-> new UserNotFoundException("User not found"));
        List<Transaction> transactions=transactionRepo.findAllByUser(user);

        BigDecimal income=BigDecimal.ZERO;
        BigDecimal expense=BigDecimal.ZERO;

        for(Transaction t:transactions)
        {
            if(t.getCategory().getType()== TransactionType.INCOME)
            {
                income=income.add(t.getAmount());
            }
            else{
                expense=expense.add(t.getAmount());
            }
        }

        BigDecimal balance=income.subtract(expense);

        DashboardResponse response=new DashboardResponse();
        response.setBalance(balance);
        response.setTotalExpense(expense);
        response.setTotalIncome(income);

        return response;


    }
}
