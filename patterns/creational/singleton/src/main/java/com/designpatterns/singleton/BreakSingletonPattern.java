package com.designpatterns.singleton;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * Break Singleton Pattern, Using
 * 1. Reflection
 * 2. Serialization
 * 3. Cloning
 */
public class BreakSingletonPattern {

    // Using Reflection

    static void breakSingletonUsingReflection() throws Exception {
        // With the help of reflection, we are accessing the private constructor
        Constructor<Singleton> constructors = Singleton.class.getDeclaredConstructor();

        // Making the private constructor accessible
        constructors.setAccessible(true);

        // Creating a new instance of Singleton using Singleton class itself
        // and using reflection
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = constructors.newInstance();

        // The hashcode of both the instances will be different
        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
        System.out.println(instance2.equals(instance1));
    }

    // Breaking Singleton using Serialization

    static void breakSingletonUsingSerialization() throws Exception {
        Singleton instance1 = Singleton.getInstance();

        // Serializing the instance
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("file"));
        oos.writeObject(instance1);
        oos.close();

        // Deserializing the instance
        ObjectInputStream ooi = new ObjectInputStream(new FileInputStream("file"));
        Singleton instance2 = (Singleton) ooi.readObject();
        ooi.close();

        // The hashcode of both the instances will be different
        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
        System.out.println(instance2.equals(instance1));
    }

    // Breaking singleton using Cloning

    static void breakSingletonUsingCloning() throws CloneNotSupportedException {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = (Singleton) instance1.clone();

        // The hashcode of both the instances will be different
        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
        System.out.println(instance2.equals(instance1));
    }
}

/**
 * Singleton class with lazy initialization
 * This class implements Serializable and Cloneable interfaces to demonstrate
 * how the singleton pattern can be broken and how to prevent it:
 * - Serializable: Allows the object to be serialized/deserialized
 * - Cloneable: Allows the object to be cloned
 * <p>
 * The class includes protection mechanisms against common singleton-breaking attacks:
 * 1. Reflection attack prevention in constructor
 * 2. Deserialization attack prevention via readResolve()
 * 3. Cloning attack prevention (currently disabled for demonstration)
 */
class Singleton implements Serializable, Cloneable {
    // Serial version UID for serialization compatibility
    private static final long serialVersionUID = 1L;

    // Flag to track if the singleton instance has already been created
    // Used to prevent reflection-based attacks on the singleton pattern
    private static boolean initialized = false;

    // The single instance of the Singleton class
    private static Singleton instance;

    /**
     * Private constructor to prevent direct instantiation
     * Includes protection against reflection attacks by checking if instance already exists
     */
    private Singleton() {
        if (initialized) {
            throw new RuntimeException("Reflection Attack detected!");
        }
        initialized = true;
    }

    /**
     * Prevents deserialization attack by returning the existing singleton instance
     * This method is called during deserialization to replace the deserialized object
     *
     * @return the existing singleton instance
     */
    protected Object readResolve() {
        return instance;
    }

    /**
     * Clone method implementation - currently allows cloning for demonstration purposes
     * In a secure singleton implementation, this should throw CloneNotSupportedException
     * to prevent breaking the singleton pattern through cloning
     *
     * @return cloned object (should be prevented in production code)
     * @throws CloneNotSupportedException when cloning is not allowed
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // Uncomment the line below to prevent cloning attacks:
        // throw new CloneNotSupportedException("Cloning of Singleton is not allowed");
        return super.clone();
    }

    /**
     * Returns the singleton instance using lazy initialization
     * Creates the instance only when first requested (not thread-safe)
     *
     * @return the singleton instance
     */
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
