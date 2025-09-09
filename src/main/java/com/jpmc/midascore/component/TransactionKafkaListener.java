package com.jpmc.midascore.component;
import com.jpmc.midascore.foundation.Transaction;
//import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// actually listens for any incoming messages and handles them
@Component
public class TransactionKafkaListener {
    private final TransactionService transactionService;

    public  TransactionKafkaListener(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    @KafkaListener(  // auto subscribes to the kafka topic
            topics="${kafka.topic.transactions}",  // reads topic name from config
            groupId = "midas-core-group", // same group ID as configured
            containerFactory="kafkaListenerContainerFactory" // uses the custom factory

    )

    public void receiveTransaction(Transaction transaction){
        System.out.println("Received transaction: " + transaction);
        System.out.println("Amount: " + transaction.getAmount());

        transactionService.processTransaction(
                transaction.getSenderId(),
                transaction.getRecipientId(),
                transaction.getAmount()
        );

    }


}
