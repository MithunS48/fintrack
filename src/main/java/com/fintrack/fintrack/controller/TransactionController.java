package com.fintrack.fintrack.controller;

import com.fintrack.fintrack.dto.transaction.TransactionRequest;
import com.fintrack.fintrack.dto.transaction.TransactionResponse;
import com.fintrack.fintrack.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public TransactionResponse makeTransaction(@Valid @RequestBody TransactionRequest request)
    {
        return transactionService.makeTransaction(request);
    }

    @GetMapping
    public List<TransactionResponse> getAllTransaction()
    {
        return transactionService.getAllTransaction();
    }

    @GetMapping("/{id}")
    public TransactionResponse getById(@PathVariable Long id)
    {
        return transactionService.getTransactionById(id);
    }

    @PutMapping("/{id}")
    public TransactionResponse updateTransaction(@PathVariable Long id,@Valid @RequestBody TransactionRequest request)
    {
        return transactionService.updateTransaction(id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id)
    {
        transactionService.deleteTransaction(id);
    }
}
