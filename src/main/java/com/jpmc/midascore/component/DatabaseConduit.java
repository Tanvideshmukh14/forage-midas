package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Component;
@Component
public class DatabaseConduit {

    private final UserRepository userRepository;

    public DatabaseConduit(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void writeTransaction(Transaction transaction) {

        UserRecord sender = userRepository.findById(transaction.getSenderId())
                .orElseThrow();

        UserRecord receiver = userRepository.findById(transaction.getReceiverId())
                .orElseThrow();

        sender.setBalance(sender.getBalance() - transaction.getAmount());
        receiver.setBalance(receiver.getBalance() + transaction.getAmount() + transaction.getIncentive());

        userRepository.save(sender);
        userRepository.save(receiver);
    }

    // ✅ Required by UserPopulator
    public void save(UserRecord user) {
        userRepository.save(user);
    }
}
