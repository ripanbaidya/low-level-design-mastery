package executorframework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Represents a unit of work executed by the thread pool.
 * Demonstrates different thread states such as RUNNABLE,
 * TIMED_WAITING (sleep), and WAITING (wait).
 */
class Task implements Runnable {
  private final int taskId;

  public Task(int taskId) {
    this.taskId = taskId;
  }

  @Override
  public void run() {
    String threadName = Thread.currentThread().getName();
    System.out.println(threadName + " - Starting task " + taskId); // RUNNABLE

    try {

      /*
       * Causes the current thread to pause for 2 seconds.
       * Thread state: RUNNABLE → TIMED_WAITING
       */
      Thread.sleep(2000);

      /*
       * The thread acquires the monitor lock of Task object
       * before entering the synchronized block.
       */
      synchronized (this) {

        System.out.println(threadName + " - WAITING on Task " + taskId);

        /*
         * Releases the monitor lock and puts the thread
         * into TIMED_WAITING state for up to 1 second.
         * After timeout (or notify), the thread competes
         * again to reacquire the lock.
         */
        this.wait(1000);
      }

      /*
       * After wait() completes, the thread becomes RUNNABLE again
       * and continues execution once scheduled by the JVM.
       */
      System.out.println(threadName + " - Task " + taskId + " Completed");

    } catch (InterruptedException e) {

      /*
       * If the thread is interrupted during sleep() or wait(), InterruptedException is
       * thrown and the interrupt status is restored here.
       */
      Thread.currentThread().interrupt();
    }
  }
}


/**
 * Demonstrates lifecycle of threads managed by a ThreadPool.
 */
public class ThreadPoolLifecycleExample {

  public static void main(String[] args) {
    /*
     * Step 1: Create a fixed thread pool with 3 worker threads.
     * These threads are reused to execute submitted tasks.
     */
    ExecutorService executor = Executors.newFixedThreadPool(3);

    System.out.println("Thread Pool Created");

    /*
     * Step 2: Submit 5 tasks.
     * Since the pool size is 3:
     * - First 3 tasks execute immediately.
     * - Remaining tasks wait in the queue.
     */
    for (int i = 1; i <= 5; i++) {
      executor.execute(new Task(i));
    }

    /*
     * Step 3: Initiates an orderly shutdown.
     * No new tasks will be accepted, but submitted tasks continue.
     */
    executor.shutdown();

    System.out.println("Thread Pool Shutdown Initiated");

    try {

      /*
       * Wait up to 10 seconds for all tasks to finish.
       */
      if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {

        /*
         * If tasks are still running, attempt an immediate shutdown.
         * Running threads receive an interrupt signal.
         */
        executor.shutdownNow();
        System.out.println("Forcing Shutdown");
      }

    } catch (InterruptedException e) {

      /*
       * If the waiting thread is interrupted,
       * force shutdown and restore interrupt status.
       */
      executor.shutdownNow();
      Thread.currentThread().interrupt();
    }

    System.out.println("All Threads Terminated");
  }
}