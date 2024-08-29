package ex00;

import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        int number = 479598;
        int sum = Stream.of(String.valueOf(number).split(""))
                .mapToInt(Integer::parseInt)
                .sum();
        System.out.println(sum);
    }
}