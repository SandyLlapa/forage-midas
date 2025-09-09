package com.jpmc.midascore.entity;
import jakarta.persistence.*;


@Entity
@Table(name="transactions")
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    @JoinColumn(name="sender_id",nullable = false)
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name="recipient_id",nullable = false)
    private UserRecord recipient;

    @Column(nullable = false)
    private  float amount;

    @Column(nullable = false)
    private boolean isValid;

    protected TransactionRecord() {
    }

        public TransactionRecord(UserRecord sender, UserRecord recipient, float amount, boolean isValid) {
        this.sender = sender;
        this.recipient=recipient;
        this.amount =  amount;
        this.isValid=isValid;
    }

    @Override
    public String toString() {
        return String.format("Transaction[id=%d, sender='%s', recipient='%s', amount='%f', valid='%b'", id, sender.getName(), recipient.getName(),amount,isValid);
    }

    public Long getId() {
        return id;
    }

    public UserRecord getSender() {
        return sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public float getAmount() {
        return amount;
    }

    public boolean isValid() {return isValid;}

    public void setSender(UserRecord sender) {
        this.sender=sender;
    }

    public void setRecipient(UserRecord recipient){
        this.recipient=recipient;
    }

    public void setAmount(float amount){
        this.amount=amount;
    }

    public void setValid(boolean isValid){
        this.isValid=isValid;
    }

}
