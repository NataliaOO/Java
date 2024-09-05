package ex02;

import java.util.Scanner;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input;
        int coffeeRequestCount = 0;
        while ((input = scanner.nextInt()) != 42) {
            if (isPrimeDigit(calculateDigitSum(input))) {
                coffeeRequestCount++;
            }
        }
        System.out.println("Count of coffee-request - " + coffeeRequestCount);
    }

    public static int calculateDigitSum(int number) {
        return Stream.of(String.valueOf(number).split(""))
                .mapToInt(Integer::parseInt).sum();
    }

    public static boolean isPrimeDigit (int number) {
        if (number <= 1) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        if (number % 2 == 0) return false;
        for (int i = 2; i * i <= number; i += 2) {
            if (number % i == 0) return true;
        }
        return true;
    }
}
