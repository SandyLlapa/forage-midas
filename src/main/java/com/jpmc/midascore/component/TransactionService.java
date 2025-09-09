package com.jpmc.midascore.component;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);


    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository){
        this.userRepository=userRepository;
        this.transactionRepository=transactionRepository;

    }

    @Transactional
    public void processTransaction(long senderId, long recipientId, float amount){
        // FIND THE USERS
        UserRecord sender=userRepository.findById(senderId);
        UserRecord recipient=userRepository.findById(recipientId);


        logger.info("\n**** PROCESSING TRANSACTION: {} -> {} : ${} *****\n",
                sender != null ? sender.getName() : "Unknown",
                recipient != null ? recipient.getName() : "Unknown",
                amount);

        // VALIDATE TRANSACTION
        boolean isValid = isValidTransaction(sender, recipient,amount);

        if (isValid && sender!=null && recipient !=null) {
            TransactionRecord transaction = new TransactionRecord(sender, recipient, amount, true);
            transactionRepository.save(transaction);


            sender.setBalance((sender.getBalance() - amount));
            recipient.setBalance((recipient.getBalance() + amount));

            userRepository.save(sender);
            userRepository.save(recipient);


            logger.info("\n**** TRANSACTION SUCCESSFUL!! New balances - {}: ${}, {}: ${}  *****\n",
                    sender.getName(), sender.getBalance(),
                    recipient.getName(), recipient.getBalance());

        }
        else{
            logger.warn("*** TRANSACTION INVALID! ***");
        }
    }

    private boolean isValidTransaction(UserRecord sender,UserRecord recipient,float amount){

        if(sender==null || recipient==null){
            return false;
        }

        if(amount<=0){
            return false;
        }

        return !(sender.getBalance() < amount);
    }

    public void printUserBalance(String username) {
        UserRecord user = userRepository.findByName(username);
        if (user != null) {
            logger.info("User {} balance: ${}", username, user.getBalance());
        } else {
            logger.warn("User {} not found", username);
        }
    }



}
