package com.designpatterns.prototype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shallow Copy: The shallow copy Copies primitive fields, Copies reference addresses
 * for objects, and here Nested objects are shared
 * Deep Copy: The deep copy Copies primitive fields, Create a new copy of nested objects,
 * and here No references are shared
 */
class Resume implements Cloneable {
    String name;
    List<String> skills;

    public Resume(String name, List<String> skills) {
        this.name = name;
        this.skills = skills;
    }

    // Shallow Copy
    /*
    @Override
    protected Resume clone() throws CloneNotSupportedException {
        return (Resume) super.clone();
    }

    */

    // Deep Copy
    @Override
    protected Resume clone() throws CloneNotSupportedException {
        Resume cloned = (Resume) super.clone(); // Shallow copy first
        cloned.skills = new ArrayList<>(this.skills); // Deep copy of list
        return cloned;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "name='" + name + '\'' +
                ", skills=" + skills +
                '}';
    }
}

public class ShallowAndDeepCopyExample {
    public static void main(String[] args) throws CloneNotSupportedException {
        Resume resume1 = new Resume("Ripan Baidya",
                new ArrayList<>(Arrays.asList("Java", "Spring Boot", "Microservices", "Kafka", "k8s"))
        );

        // Creating copy
        Resume resume2 = resume1.clone();

        // Apply changes
        resume2.skills.remove("Go");
        resume2.name = "John Doe";

        System.out.println(resume1);
        System.out.println(resume2);
    }
}
