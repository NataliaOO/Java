package ex04;

import java.util.Arrays;

public class UsersArrayList implements UsersList {
    private User[] users;
    private int size;
    private int capacity;


    private static final int INITIAL_CAPACITY = 10;

    public UsersArrayList() {
        this.capacity = INITIAL_CAPACITY;
        this.users = new User[capacity];
        this.size = 0;
    }

    @Override
    public void addUser(User user) {
        if (size == capacity) {
            capacity += capacity / 2;
            users = Arrays.copyOf(users, capacity);
        }
        users[size++] = user;
    }

    @Override
    public User getUserById(int id) {
        for (int i = 0; i < size; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        throw new ex02.UserNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User getUserByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new UserNotFoundException("User with index " + index + " not found");
        }
        return users[index];
    }

    @Override
    public int getNumberOfUsers() {
        return size;
    }
}
