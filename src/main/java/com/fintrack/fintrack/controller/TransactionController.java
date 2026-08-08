package com.fintrack.fintrack.controller;

import com.fintrack.fintrack.dto.transaction.TransactionRequest;
import com.fintrack.fintrack.dto.transaction.TransactionResponse;
import com.fintrack.fintrack.enums.TransactionType;
import com.fintrack.fintrack.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public TransactionResponse makeTransaction(@AuthenticationPrincipal UserDetails userDetails,
                                               @Valid @RequestBody TransactionRequest request)
    {
        return transactionService.makeTransaction(userDetails,request);
    }

    @GetMapping
    public List<TransactionResponse> getAllTransaction(@AuthenticationPrincipal UserDetails userDetails,
                                                       @RequestParam(required = false)TransactionType type,
                                                       @RequestParam(required = false)Long categoryId,
                                                       @RequestParam(required = false)LocalDate startDate,
                                                       @RequestParam(required = false)LocalDate endDate,
                                                       @RequestParam(defaultValue = "0")int page,
                                                       @RequestParam(defaultValue = "10")int size)
    {
        return transactionService.getAllTransactions(userDetails,type,categoryId,startDate,endDate,page,size);
    }



    @PutMapping("/{id}")
    public TransactionResponse updateTransaction(@AuthenticationPrincipal UserDetails userDetails,
                                                 @PathVariable Long id,
                                                 @Valid @RequestBody TransactionRequest request)
    {
        return transactionService.updateTransaction(userDetails,id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable Long id)
    {
        transactionService.deleteTransaction(userDetails,id);
    }
}
