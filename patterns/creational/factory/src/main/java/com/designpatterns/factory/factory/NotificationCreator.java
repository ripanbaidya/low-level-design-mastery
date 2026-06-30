package com.designpatterns.factory.factory;

import com.designpatterns.factory.model.Notification;

/**
 * Abstract Creator class that defines the factory method pattern.
 * This class serves as the base for concrete notification creators.
 * It provides a common interface for creating and sending notifications
 * while delegating the actual creation logic to subclasses.
 */
public abstract class NotificationCreator {

    /**
     * Factory method that creates a notification instance.
     * This is an abstract method that must be implemented by concrete subclasses.
     * Each concrete creator will decide what specific type of notification to create.
     *
     * @return Notification instance - the specific type depends on the concrete implementation
     */
    public abstract Notification createNotification();

    /**
     * Template method that handles the complete notification sending process.
     * This method uses the factory method to create a notification and then sends it.
     * The creation logic is delegated to subclasses via the createNotification() method.
     *
     * @param message the message content to be sent through the notification
     */
    public void sendNotification(String message) {
        // Use the factory method to create the appropriate notification type
        Notification notification = createNotification();

        // Send the notification with the provided message
        notification.send(message);
    }
}