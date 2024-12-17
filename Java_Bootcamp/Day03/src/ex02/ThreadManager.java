package ex02;

public class ThreadManager {
    private final int[] array;
    private final int threadCount;
    private final int[] partialSum;
    private final Thread[] threads;

    public ThreadManager(int[] array, int threadCount) {
        this.array = array;
        this.threadCount = threadCount;
        this.partialSum = new int[threadCount];
        this.threads = new Thread[threadCount];
    }

    public void runTreads() {
        int sectionSize = array.length / threadCount;
        for (int i = 0; i < threadCount; i++) {
            int start = i * sectionSize;
            int end = (i == threadCount - 1) ? array.length : start + sectionSize;

            threads[i] = new SummingThread(array, start, end, partialSum, i);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getTotalSum() {
        int sum = 0;
        for (int pSum : partialSum) {
            sum += pSum;
        }
        return sum;
    }
}
