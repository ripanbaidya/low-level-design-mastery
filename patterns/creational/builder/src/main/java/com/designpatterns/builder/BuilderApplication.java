package com.designpatterns.builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuilderApplication {

    public static void main(String[] args) {
        // SpringApplication.run(BuilderApplication.class, args);

        // Execute clean, expressive, step-by-step object configuration
        Post analyticalPost = new Post.Builder()
                .title("Mastering the Builder Pattern")
                .content("A deep dive into creational software design principles.")
                .author("Sarah Connor")
                .category("Software Architecture")
                // imagePreviewUrl is omitted here; it gracefully defaults to null without error
                .build();

        System.out.println("Successfully instantiated product:");
        System.out.println(analyticalPost);
    }
}
