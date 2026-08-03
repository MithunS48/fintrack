package com.fintrack.fintrack.service;

import com.fintrack.fintrack.dto.report.CategoryReportResponse;
import com.fintrack.fintrack.entity.Category;
import com.fintrack.fintrack.entity.Transaction;
import com.fintrack.fintrack.entity.User;
import com.fintrack.fintrack.exception.CategoryNotFoundException;
import com.fintrack.fintrack.exception.UserNotFoundException;
import com.fintrack.fintrack.repository.CategoryRepo;
import com.fintrack.fintrack.repository.TransactionRepo;
import com.fintrack.fintrack.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryReportService {

    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final TransactionRepo transactionRepo;

    public List<CategoryReportResponse> getByCategory(UserDetails userDetails)
    {
        List<CategoryReportResponse>  list=new ArrayList<>();
        User user=userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()->new UserNotFoundException("user not found"));
        List<Category> category=categoryRepo.findByUserOrUserIsNull(user);



        for(Category c:category)
        {
            BigDecimal total = BigDecimal.ZERO;
            List<Transaction> transactions=transactionRepo.findAllByUserAndCategory(user,c);
            for (Transaction t : transactions) {
                total = total.add(t.getAmount());
            }
            CategoryReportResponse response=new CategoryReportResponse();

            response.setCategoryName(c.getName());
            response.setCategoryType(c.getType());
            response.setTotalAmount(total);
            if (total.compareTo(BigDecimal.ZERO) > 0) {
                list.add(response);
            }
        }

        return list;

    }

}
