package ex00;
import java.util.UUID;

public class User {
    private final UUID id;
    private String name;
    private int balance;

    public User(String name, int balance) {
        this.id = UUID.randomUUID();
        this.name = name;
        setBalance(balance);
    }

    public UUID getId() {
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
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", balance=" + balance + "]";
    }
}
