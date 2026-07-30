package com.fintrack.fintrack.service;

import com.fintrack.fintrack.dto.transaction.TransactionRequest;
import com.fintrack.fintrack.dto.transaction.TransactionResponse;
import com.fintrack.fintrack.entity.Category;
import com.fintrack.fintrack.entity.Transaction;
import com.fintrack.fintrack.entity.User;
import com.fintrack.fintrack.exception.CategoryNotFoundException;
import com.fintrack.fintrack.exception.TransactionNotFoundException;
import com.fintrack.fintrack.exception.UserNotFoundException;
import com.fintrack.fintrack.repository.CategoryRepo;
import com.fintrack.fintrack.repository.TransactionRepo;
import com.fintrack.fintrack.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
@Setter
@Getter
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;


    public TransactionResponse makeTransaction(TransactionRequest request)
    {
        User user=userRepo.findById(request.getUserId()).orElseThrow(()->new UserNotFoundException("user not found"));
        Category category=categoryRepo.findById(request.getCategoryId()).orElseThrow(()->new CategoryNotFoundException("user not found"));

        Transaction transaction=new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDateTime(LocalDateTime.now());
        transaction.setDescription(request.getDescription());
        transaction.setUser(user);
        transaction.setCategory(category);

        Transaction save=transactionRepo.save(transaction);

        TransactionResponse response=new TransactionResponse();
        response.setId(save.getId());
        response.setAmount(save.getAmount());
        response.setDescription(save.getDescription());
        response.setTransactionDateTime(save.getTransactionDateTime());
        response.setCategoryType(save.getCategory().getType());
        response.setCategoryName(save.getCategory().getName());

        return response;

    }


    public List<TransactionResponse> getAllTransaction()
    {
        List<TransactionResponse> list=new ArrayList<>();
        List<Transaction> transactions=transactionRepo.findAll();

        for(Transaction t:transactions)
        {
            TransactionResponse response=new TransactionResponse();
            response.setId(t.getId());
            response.setAmount(t.getAmount());
            response.setDescription(t.getDescription());
            response.setTransactionDateTime(t.getTransactionDateTime());
            response.setCategoryType(t.getCategory().getType());
            response.setCategoryName(t.getCategory().getName());

            list.add(response);
        }
        return list;
    }

    public TransactionResponse getTransactionById(Long id)
    {
        Transaction t=transactionRepo.findById(id).orElseThrow(()->new TransactionNotFoundException("no Transaction found"));
        TransactionResponse response=new TransactionResponse();
        response.setId(t.getId());
        response.setAmount(t.getAmount());
        response.setDescription(t.getDescription());
        response.setTransactionDateTime(t.getTransactionDateTime());
        response.setCategoryType(t.getCategory().getType());
        response.setCategoryName(t.getCategory().getName());


        return response;

    }
    public TransactionResponse updateTransaction(Long id, TransactionRequest request)
    {
        User user=userRepo.findById(request.getUserId()).orElseThrow(()->new UserNotFoundException("user not found"));
        Category category=categoryRepo.findById(request.getCategoryId()).orElseThrow(()->new CategoryNotFoundException("category not found"));
        Transaction transaction=transactionRepo.findById(id).orElseThrow(()->new TransactionNotFoundException("no Transaction found"));
        transaction.setAmount(request.getAmount());

        transaction.setDescription(request.getDescription());
        transaction.setUser(user);
        transaction.setCategory(category);
        Transaction save=transactionRepo.save(transaction);

        TransactionResponse response=new TransactionResponse();
        response.setId(save.getId());
        response.setAmount(save.getAmount());
        response.setDescription(save.getDescription());
        response.setTransactionDateTime(save.getTransactionDateTime());
        response.setCategoryType(save.getCategory().getType());
        response.setCategoryName(save.getCategory().getName());

        return response;

    }

    public void deleteTransaction(Long id)
    {
        Transaction transaction=transactionRepo.findById(id).orElseThrow(()->new TransactionNotFoundException("no Transaction found"));
        transactionRepo.deleteById(transaction.getId());
    }
}
