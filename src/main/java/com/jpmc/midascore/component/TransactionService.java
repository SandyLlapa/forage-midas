package com.jpmc.midascore.component;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
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
    private final IncentiveService incentiveService;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository, IncentiveService incentiveService){
        this.userRepository=userRepository;
        this.transactionRepository=transactionRepository;
        this.incentiveService = incentiveService;

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

        Transaction apiTransaction = new Transaction(senderId,recipientId,amount);
        float incentiveAmount = incentiveService.getIncentiveAmount(apiTransaction);

        logger.info("****  INCENTIVE AMOUNT: ${} ****", incentiveAmount);


        if (sender != null && recipient != null) {
            TransactionRecord transaction = new TransactionRecord(
                    sender, recipient, amount, incentiveAmount, isValid
            );
            transactionRepository.save(transaction);
        }

        if (isValid && sender!=null && recipient !=null) {


            sender.setBalance((sender.getBalance() - amount));
            recipient.setBalance((recipient.getBalance() + amount +incentiveAmount));

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

    public float getBalanceByUsername(String username) {
        UserRecord user = userRepository.findByName(username);
        return user != null ? user.getBalance() : 0f;
    }

}
