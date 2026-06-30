package com.designpatterns.singleton;

public enum EnumSingleton {

    // JVM guarantees a single instance, inherently thread-safe and safe from attacks
    INSTANCE;

    public void executeBusinessLogic() {
        System.out.println("Executing system operations securely.");
    }
}
