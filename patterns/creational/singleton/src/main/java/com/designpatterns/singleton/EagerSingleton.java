package com.designpatterns.singleton;

public class EagerSingleton {

    // The instance is initialized when the class loader loads this class into memory
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    // Prevent Instantiation
    private EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
