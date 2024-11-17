package ex05;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final TransactionsService transactionsService;
    private final boolean isDevMode;
    private final Scanner scanner;

    public Menu(TransactionsService transactionsService, boolean isDevMode) {
        this.transactionsService = transactionsService;
        this.isDevMode = isDevMode;
        this.scanner = new Scanner(System.in);
    }

    private void displayMenu() {
        System.out.println("---------------------------------------------------------");
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (isDevMode) {
            System.out.println("5. DEV – remove a transfer by ID");
            System.out.println("6. DEV – check transfer validity");
        }
        System.out.println("7. Finish execution");
        System.out.print("-> ");
    }

    public void run() {
        while (true) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: addUser(); break;
                    case 2: viewUserBalancer(); break;
                    case 3: performTransfer(); break;
                    case 4: viewUserTransactions(); break;
                    case 5: {
                        if (isDevMode) removeTransactionById();
                    } break;
                    case 6: {
                        if (isDevMode) checkTransferValidity();
                    } break;
                    case 7: exitMenu(); break;
                    default:
                        System.err.println("Invalid option. Please enter a valid command number.");
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage() + ". Please try again.");
            }
        }
    }

    private void addUser() {
        System.out.print("Enter a user name and a balance\n-> ");
        String userName = scanner.nextLine();
        String[] parts = userName.split(" ");
        User user = new User(parts[0],Integer.parseInt(parts[1]));
        transactionsService.addUser(user);
        System.out.println("User with id = " + user.getId() + " is added");
    }

    private void viewUserBalancer() {
        System.out.print("Enter a user ID\n-> ");
        int userId = Integer.parseInt(scanner.nextLine());

        int balance = transactionsService.getUserBalance(userId);
        String user = transactionsService.getUserNameById(userId);
        System.out.println(user + " - " + balance);
    }

    private void performTransfer() {
        System.out.print("Enter a sender ID, a recipient ID, and a transfer amount\n-> ");
        String[] inputs = scanner.nextLine().split(" ");
        int senderId = Integer.parseInt(inputs[0]);
        int receiverId = Integer.parseInt(inputs[1]);
        int amount = Integer.parseInt(inputs[2]);
        transactionsService.performTransfer(senderId,receiverId,amount);
        System.out.println("The transfer is completed");
    }

    private void viewUserTransactions() {
        System.out.print("Enter a user ID\n-> ");
        int userId = Integer.parseInt(scanner.nextLine());

        Transaction[] transactions = transactionsService.getUserTransfers(userId);
        for (Transaction transaction : transactions) {
            System.out.printf("To %s(id = %d) %s with id = %s\n", transaction.getRecipient().getName(),
                    transaction.getRecipient().getId(), transaction.getAmount(), transaction.getId());
        }
    }

    private void removeTransactionById() {
        System.out.print("Enter a user ID and a transfer ID\n-> ");
        String[] inputs = scanner.nextLine().split(" ");
        int userId = Integer.parseInt(inputs[0]);
        UUID transactionId = UUID.fromString(inputs[1]);

        Transaction transaction = Arrays.stream(transactionsService.getUserTransfers(userId))
                .filter(t -> t.getId().equals(transactionId))
                .findFirst()
                .orElse(null);

        if (transaction != null) {
            transactionsService.removeUserTransaction(userId, transactionId);
            User recipient = transaction.getRecipient();
            System.out.printf("Transfer To %s(id = %d) %d removed%n",
                    recipient.getName(), recipient.getId(), -transaction.getAmount());
        } else {
            System.out.printf("Transaction with id = %s not found.%n", transactionId);
        }
    }

    private void checkTransferValidity() {
        System.out.println("Check results:");
        Transaction[] unpairedTransactions = transactionsService.validateTransactions();
        if (unpairedTransactions.length == 0) {
            System.out.println("All transactions are paired.");
        } else {
            for (Transaction transaction : unpairedTransactions) {
                User recipient = transaction.getRecipient();
                User sender = transaction.getSender();
                System.out.printf("%s(id = %d) has an unacknowledged transfer id = %s from %s(id = %d) for %d%n",
                        sender.getName(), sender.getId(),
                        transaction.getId(), recipient.getName(),
                        recipient.getId(), transaction.getAmount());
            }
        }
    }

    private void exitMenu() {
        System.out.println("Exiting program.");
        System.exit(0);
    }
}
