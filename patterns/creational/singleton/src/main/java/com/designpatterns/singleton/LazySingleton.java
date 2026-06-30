package com.designpatterns.singleton;

public class LazySingleton {

    private static LazySingleton instance = null;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        // Condition is vulnerable to race conditions if multiple threads enter simultaneously
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
