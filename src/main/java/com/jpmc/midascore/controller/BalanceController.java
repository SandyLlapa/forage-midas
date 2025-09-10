package com.jpmc.midascore.controller;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.component.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final TransactionService transactionService;

    public BalanceController(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    @GetMapping
    public Balance getBalance(@RequestParam long userId){
        float balanceAmount =  transactionService.getBalanceByUserId(userId);
        return new Balance(balanceAmount);
    }


}
