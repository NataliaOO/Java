package ex01;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if ( n <= 1 ) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        int i = 2;
        while (i * i <= n && n % i != 0) {
            i++;
        }
        System.out.println((i * i > n) + " " + --i);
    }
}
