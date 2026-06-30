package com.designpatterns.prototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // SpringApplication.run(Application.class, args);

        Apartment luxuryBlueprint =
                new Apartment("White", new Furniture("Fabric Sofa"));

        // Customer 1
        Apartment customerOne = luxuryBlueprint.clone();
        customerOne.setWallColor("Blue");
        customerOne.getFurniture().setSofa("Leather Sofa");

        // Customer 2
        Apartment customerTwo = luxuryBlueprint.clone();
        customerTwo.setWallColor("Grey");
        
        System.out.println("Blueprint");
        luxuryBlueprint.display();

        System.out.println();

        System.out.println("Customer One");
        customerOne.display();

        System.out.println();

        System.out.println("Customer Two");
        customerTwo.display();
    }
}
