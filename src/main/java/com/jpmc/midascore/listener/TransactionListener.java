package com.jpmc.midascore.listener;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.service.IncentiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionListener {

    @Autowired
    private DatabaseConduit databaseConduit;

    @Autowired
    private IncentiveService incentiveService;

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
    public void listen(Transaction transaction) {

        // ✅ Generate unique ID if missing
        if (transaction.getId() == null) {
            transaction.setId(java.util.UUID.randomUUID().toString());
        }

        float incentive = incentiveService.fetchIncentive(transaction);
        transaction.setIncentive(incentive);

        databaseConduit.writeTransaction(transaction);

        System.out.println("✅ Processed transaction: " + transaction.getId());
    }


}
