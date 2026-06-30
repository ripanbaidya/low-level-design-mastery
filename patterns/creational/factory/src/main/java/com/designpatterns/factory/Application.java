package com.designpatterns.factory;

import com.designpatterns.factory.factory.EmailNotificationCreator;
import com.designpatterns.factory.factory.NotificationCreator;
import com.designpatterns.factory.factory.PushNotificationCreator;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // SpringApplication.run(FactoryApplication.class, args);

        NotificationCreator creator = null;

        // Email notification
        creator = new EmailNotificationCreator();
        creator.sendNotification("You got offer from Amazon!");

        // Push notification
        creator = new PushNotificationCreator();
        creator.sendNotification("Congratulations!!");
    }
}
