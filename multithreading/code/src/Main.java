class SharedResource {
  synchronized void waitExample() {
    System.out.println(Thread.currentThread().getName() + " is waiting...");
    try {
      wait(); // Releases the lock and waits
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    System.out.println(Thread.currentThread().getName() + " resumed after notify.");
  }


  synchronized void notifyExample() {
    System.out.println("Notifying a waiting thread...");
    notify(); // Wakes up one waiting thread
  }
}


public class Main {
  public static void main(String[] args) {
    SharedResource shared = new SharedResource();
    // Thread 1 (Waits)
    Thread t1 = new Thread(shared::waitExample, "Thread-1");

    // Thread 2 (Notifies after 2 seconds)
    Thread t2 = new Thread(() -> {
      try {
        Thread.sleep(2000); // Ensure Thread-1 goes to wait state
        shared.notifyExample();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }, "Thread-2");


    t1.start();
    t2.start();
  }
}