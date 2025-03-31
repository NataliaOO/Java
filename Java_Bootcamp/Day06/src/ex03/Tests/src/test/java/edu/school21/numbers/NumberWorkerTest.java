package edu.school21.numbers;

import edu.school21.exceptions.IllegalNumberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5})
    void isPrimeForPrimes(int number) {
        NumberWorker worker = new NumberWorker();
        assertTrue(worker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 8, 10})
    void isPrimeForNotPrimes(int number) {
        NumberWorker worker = new NumberWorker();
        assertFalse(worker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void isPrimeForIncorrectNumbers(int number) {
        NumberWorker worker = new NumberWorker();
        assertThrows(IllegalNumberException.class, () -> worker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 0)
    void digitSumTest(int number, int expected) {
        NumberWorker worker = new NumberWorker();
        assertEquals(expected, worker.digitsSum(number));
    }
}
