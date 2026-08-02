package com.fintrack.fintrack.controller;

import com.fintrack.fintrack.dto.transaction.TransactionRequest;
import com.fintrack.fintrack.dto.transaction.TransactionResponse;
import com.fintrack.fintrack.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public TransactionResponse makeTransaction(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody TransactionRequest request)
    {
        return transactionService.makeTransaction(userDetails,request);
    }

    @GetMapping
    public List<TransactionResponse> getAllTransaction(@AuthenticationPrincipal UserDetails userDetails)
    {
        return transactionService.getAllTransactions(userDetails);
    }



    @PutMapping("/{id}")
    public TransactionResponse updateTransaction(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long id,@Valid @RequestBody TransactionRequest request)
    {
        return transactionService.updateTransaction(userDetails,id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long id)
    {
        transactionService.deleteTransaction(userDetails,id);
    }
}
