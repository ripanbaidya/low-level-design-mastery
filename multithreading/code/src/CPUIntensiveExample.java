import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CPUIntensiveExample {
  private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

  public static void main(String[] args) {
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(NUMBER_OF_CORES);
    for (int i = 0; i < 10; i++) {
      fixedThreadPool.execute(() -> {
        int result = performComputation();
        System.out.println(Thread.currentThread().getName() + " Computed result: " + result);
      });
    }

    fixedThreadPool.shutdown();
  }

  /**
   * Imagine, Heavy computation task
   */
  private static int performComputation() {
    int sum = 0;
    for (int i = 1; i <= 100000; i++) {
      sum += i;
    }
    return sum;
  }
}
