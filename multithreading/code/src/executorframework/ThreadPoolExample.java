package executorframework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample implements Runnable {

  private final int taskId;

  public ThreadPoolExample(int taskId) {
    this.taskId = taskId;
  }

  @Override
  public void run() {
    String threadName = Thread.currentThread().getName();
    System.out.println(threadName + " is processing task " + taskId);
//    try {
//      Thread.sleep(2000);
//    } catch (InterruptedException e) {
//      Thread.currentThread().interrupt();
//    }
    System.out.println(threadName + " finished task " + taskId);
  }

  public static void main(String[] args) {
    // Crate a thread pool with 3 threads
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    // Submit 6 tasks to the thread pool
    for (int i = 1; i <= 6; i++) {
      executorService.submit(new ThreadPoolExample(i));
    }

    // Shutdown the executor service
    executorService.shutdown();
  }
}
