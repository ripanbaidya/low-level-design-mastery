package com.designpatterns.singleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SingletonApplication {
    public static void main(String[] args) {
        // SpringApplication.run(SingletonApplication.class, args);
        DoubleCheckedLockingSingleton d1 = DoubleCheckedLockingSingleton.getInstance();
        DoubleCheckedLockingSingleton d2 = DoubleCheckedLockingSingleton.getInstance();

        System.out.println(d1.hashCode());
        System.out.println(d2.hashCode());
        System.out.println(d1 == d2);
    }
}
