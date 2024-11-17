package ex01;

public class Program {
    public static void main(String[] args) {
        User user1 = new User( "Alice", 500);
        User user2 = new User( "Bob", 300);
        User user3 = new User( "Alice", 500);
        User user4 = new User( "Alice", 500);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        System.out.println(user4);
    }
}
