package ex03;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("Alice", 500);
        User user2 = new User("Bob", 300);

        Transaction transaction1 = new Transaction(user2, user1, Transaction.Category.DEBIT, -100);
        Transaction transaction2 = new Transaction(user1, user2, Transaction.Category.CREDIT, 100);

        user1.getTransactions().addTransaction(transaction1);
        user1.getTransactions().addTransaction(transaction2);

        System.out.println("Transactions for user: " + user1.getName());
        for (Transaction transaction : user1.getTransactions().toArray()) {
            System.out.println(transaction);
        }

        UUID transactionIdToRemove = transaction1.getId();
        user1.getTransactions().removeTransactionById(transactionIdToRemove);
        System.out.println("Transaction with ID " + transactionIdToRemove + " removed.");

        System.out.println("Updated transactions for user: " + user1.getName());
        for (Transaction transaction : user1.getTransactions().toArray()) {
            System.out.println(transaction);
        }

        try {
            user1.getTransactions().removeTransactionById(transactionIdToRemove); // Non-existent ID
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
