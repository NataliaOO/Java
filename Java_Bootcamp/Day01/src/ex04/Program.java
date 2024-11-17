package ex04;

public class Program {
    public static void main(String[] args) {
        TransactionsService transactionsService = new TransactionsService();

        User alice = new User("Alice", 1000);
        User bob = new User("Bob", 500);
        transactionsService.addUser(alice);
        transactionsService.addUser(bob);

        System.out.println("Alice's balance: " + transactionsService.getUserBalance(alice.getId()));
        System.out.println("Bob's balance: " + transactionsService.getUserBalance(bob.getId()));

        transactionsService.performTransfer(alice.getId(), bob.getId(), 200);

        System.out.println("After transfer:");
        System.out.println("Alice's balance: " + transactionsService.getUserBalance(alice.getId()));
        System.out.println("Bob's balance: " + transactionsService.getUserBalance(bob.getId()));

        System.out.println("Alice's transactions:");
        for (Transaction transaction : transactionsService.getUserTransfers(alice.getId())) {
            System.out.println(transaction);
        }

        System.out.println("Bob's transactions:");
        for (Transaction transaction : transactionsService.getUserTransfers(bob.getId())) {
            System.out.println(transaction);
        }

        System.out.println("Validating transactions...");
        Transaction[] unpairedTransactions = transactionsService.validateTransactions();
        if (unpairedTransactions.length == 0) {
            System.out.println("All transactions are paired.");
        } else {
            System.out.println("Unpaired transactions found:");
            for (Transaction transaction : unpairedTransactions) {
                System.out.println(transaction);
            }
        }

        try {
            transactionsService.performTransfer(bob.getId(), alice.getId(), 800);
        } catch (IllegalTransactionException e) {
            System.err.println(e.getMessage());
        }
    }
}
