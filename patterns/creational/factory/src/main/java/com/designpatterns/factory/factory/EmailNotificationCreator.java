package com.designpatterns.factory.factory;

import com.designpatterns.factory.implementation.EmailNotification;
import com.designpatterns.factory.model.Notification;

public class EmailNotificationCreator extends NotificationCreator {

    @Override
    public Notification createNotification() {
        return new EmailNotification();
    }
}
