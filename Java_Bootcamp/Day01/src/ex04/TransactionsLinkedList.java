package ex04;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Node head;
    private int size;

    private static class Node {
        Transaction transaction;
        Node next;
        Node(Transaction transaction) {
            this.transaction = transaction;
        }
    }

    public TransactionsLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        Node newNode = new Node(transaction);
        newNode.next = head;
        head = newNode;
        size++;
    }

    @Override
    public void removeTransactionById(UUID transactionId) {
        Node currentNode = head;
        Node previousNode = null;
        while (currentNode != null) {
            if (currentNode.transaction.getId().equals(transactionId)) {
                if (previousNode == null) {
                    head = currentNode.next;
                } else {
                    previousNode.next = currentNode.next;
                }
                size--;
                return;
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
        throw new RuntimeException("Transaction with ID " + transactionId + " not found.");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];
        Node current = head;
        int i = 0;
        while (current != null) {
            transactions[i++] = current.transaction;
            current = current.next;
        }
        return transactions;
    }
}
