package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"transactions"},
        brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@TestPropertySource(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class TaskThreeTests {

    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRepository userRepo;

    @Test
    void task_three_verifier() throws InterruptedException {
        // Create sample users
        userRepo.save(new UserRecord("waldorf", 1000));
        userRepo.save(new UserRecord("statler", 1000));

        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String line : transactionLines) {
            kafkaProducer.send(line);
        }

        Thread.sleep(2000); // wait for listener to process

        float waldorfBalance = userRepo.findByName("waldorf").getBalance();
        logger.info("Waldorf's balance after all transactions: {}", Math.floor(waldorfBalance));
    }
}
