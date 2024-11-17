package ex00;

public class Program {
    public static void main(String[] args) {
        User user1 = new User( "Alice", 500);
        User user2 = new User( "Bob", 300);
        System.out.println(user1);
        System.out.println(user2);

        Transaction transaction1 = new Transaction(user2, user1, Transaction.Category.DEBIT, -100);
        Transaction transaction2 = new Transaction(user1, user2, Transaction.Category.CREDIT, 100);
        System.out.println(transaction1);
        System.out.println(transaction2);

        user1.setBalance(user1.getBalance() - 100);
        user2.setBalance(user2.getBalance() + 100);
        System.out.println("Updated Balances:");
        System.out.println(user1);
        System.out.println(user2);
    }
}