package ex02;

public class Program {
    public static void main(String[] args) {
        UsersList userList = new UsersArrayList();

        User user1 = new User("Alice", 500);
        User user2 = new User("Bob", 300);
        userList.addUser(user1);
        userList.addUser(user2);

        System.out.println("Number of users: " + userList.getNumberOfUsers());

        System.out.println("User at index 0: " + userList.getUserByIndex(0));
        System.out.println("User at index 1: " + userList.getUserByIndex(1));

        System.out.println("User with ID " + user1.getId() + ": " + userList.getUserById(user1.getId()));
        System.out.println("User with ID " + user2.getId() + ": " + userList.getUserById(user2.getId()));

        try {
            userList.getUserById(999);
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }

        for (int i = 0; i < userList.getNumberOfUsers(); i++) {
            System.out.println(userList.getUserByIndex(i));
        }
    }
}
