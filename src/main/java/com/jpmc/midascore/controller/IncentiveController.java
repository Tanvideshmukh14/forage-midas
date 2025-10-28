package com.jpmc.midascore.controller;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.web.bind.annotation.*;

@RestController
public class IncentiveController {

    @PostMapping("/incentive")
    public Transaction calculateIncentive(@RequestBody Transaction transaction) {

        System.out.println("Received Transaction to process incentive: " + transaction);

        Float calculatedIncentive = transaction.getAmount() * 0.01f;
        transaction.setIncentive(calculatedIncentive);

        return transaction;
    }
}
