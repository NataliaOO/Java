package ex04;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionsService {
    private final UsersList usersList = new UsersArrayList();

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public int getUserBalance(int userId) {
        User user = usersList.getUserById(userId);
        return user.getBalance();
    }

    public void performTransfer(int senderId, int receiverId, int amount) {
        if (amount <= 0) {
            throw new IllegalTransactionException("Transfer amount must be positive.");
        }

        User sender = usersList.getUserById(senderId);
        User receiver = usersList.getUserById(receiverId);

        if (sender.getBalance() < amount) {
            throw new IllegalTransactionException("Insufficient balance for transfer.");
        }

        Transaction debitTransaction = new Transaction(receiver, sender, Transaction.Category.DEBIT, -amount);
        Transaction creditTransaction = new Transaction(sender, receiver, Transaction.Category.CREDIT, amount);

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        sender.getTransactions().addTransaction(debitTransaction);
        receiver.getTransactions().addTransaction(creditTransaction);
    }

    public Transaction[] getUserTransfers(int userId) {
        User user = usersList.getUserById(userId);
        return user.getTransactions().toArray();
    }

    public void removeUserTransaction(int userId, UUID transactionId) {
        User user = usersList.getUserById(userId);
        user.getTransactions().removeTransactionById(transactionId);
    }

    public Transaction[] validateTransactions() {
        List<Transaction> unpairedTransactions = new ArrayList<>();

        for (int i = 0; i < usersList.getNumberOfUsers(); i++) {
            User user = usersList.getUserByIndex(i);
            Transaction[] transactions = user.getTransactions().toArray();

            for (Transaction transaction : transactions) {
                boolean paired = false;

                for (int j = 0; j < usersList.getNumberOfUsers(); j++) {
                    if (i == j) continue;

                    User otherUser = usersList.getUserByIndex(j);
                    Transaction[] otherUserTransactions = otherUser.getTransactions().toArray();

                    for (Transaction otherTransaction : otherUserTransactions) {
                        if (transaction.getId().equals(otherTransaction.getId())
                                && transaction.getCategory() != otherTransaction.getCategory()) {
                            paired = true;
                            break;
                        }
                    }

                    if (paired) break;
                }

                if (!paired) {
                    unpairedTransactions.add(transaction);
                }
            }
        }

        return unpairedTransactions.toArray(new Transaction[0]);
    }
}
