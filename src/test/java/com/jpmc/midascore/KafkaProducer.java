package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final String topic;
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public KafkaProducer(@Value("${general.kafka-topic}") String topic,
                         KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }
    public void send(String line) {
        String[] parts = line.split(",\\s*");

        long sender = Long.parseLong(parts[0]);
        long receiver = Long.parseLong(parts[1]);
        float amount = Float.parseFloat(parts[2]);

        float incentive = 0f; // default
        if (parts.length > 3) {
            incentive = Float.parseFloat(parts[3]);
        }

        Transaction tx = new Transaction(sender, receiver, amount, incentive);
        kafkaTemplate.send(topic, tx);
    }

}
