package ex04;

import java.util.UUID;

public class Transaction {
    public enum Category {
        DEBIT,
        CREDIT
    }

    private final UUID id;
    private User recipient;
    private User sender;
    private Category category;
    private int amount;

    public Transaction(User recipient, User sender, Category category, int amount) {
        this.id = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.category = category;
        setAmount(amount);
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (category == Category.DEBIT && amount > 0) {
            throw new IllegalArgumentException("Debit transactions must have a negative amount.");
        } else if (category == Category.CREDIT && amount < 0) {
            throw new IllegalArgumentException("Credit transactions must have a positive amount.");
        }
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", recipient=" + recipient.getName() + ", sender=" + sender.getName() +
                ", category=" + category + ", amount=" + amount + "]";
    }
}
