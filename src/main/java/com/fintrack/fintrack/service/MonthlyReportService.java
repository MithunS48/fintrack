package com.fintrack.fintrack.service;

import com.fintrack.fintrack.dto.report.MonthlyReportResponse;
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
public class MonthlyReportService {

    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;

    public MonthlyReportResponse getMonthlyReport(UserDetails userDetails,int month,int year)
    {
        User user=userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()->new UserNotFoundException("user not found"));
        List<Transaction> transactions=transactionRepo.findAllByUser(user);

        BigDecimal income=BigDecimal.ZERO;
        BigDecimal expense=BigDecimal.ZERO;
        BigDecimal balance=BigDecimal.ZERO;
        for(Transaction t:transactions)
        {

            if(t.getTransactionDateTime().getMonthValue()==month && t.getTransactionDateTime().getYear()==year)
            {
                if(t.getCategory().getType()== TransactionType.EXPENSE)
                {
                    expense=expense.add(t.getAmount());
                }
                else {
                    income=income.add(t.getAmount());
                }
            }


        }
        balance=income.subtract(expense);

        MonthlyReportResponse response=new MonthlyReportResponse();
        response.setTotalIncome(income);
        response.setTotalExpense(expense);
        response.setBalance(balance);
        return response;
    }
}
