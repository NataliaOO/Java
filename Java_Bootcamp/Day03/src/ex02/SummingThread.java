package ex02;

public class SummingThread extends Thread {
    private final int[] array;
    private final int start;
    private final int end;
    private final int[] partialSum;
    private final int index;

    public SummingThread(int[] array, int start, int end, int[] partialSum, int index) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.partialSum = partialSum;
        this.index = index;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        partialSum[index] = sum;
        System.out.printf("Thread %d: from %d to %d sum is %d%n", index + 1, start, end - 1, sum);
    }
}
