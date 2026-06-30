package com.designpatterns.singleton;

public class ThreadSafeSingleton {

    private static ThreadSafeSingleton instance = null;

    private ThreadSafeSingleton() {
    }

    // The 'synchronized' keyword prevents concurrent access but penalizes performance
    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
}
