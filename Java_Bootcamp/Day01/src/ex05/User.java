package ex05;

public class User {
    private final int id;
    private String name;
    private int balance;
    private TransactionsList transactions;

    public User(String name, int balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        setBalance(balance);
        this.transactions = new TransactionsLinkedList();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = balance;
    }

    public TransactionsList getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", balance=" + balance + "]";
    }
}
