package com.designpatterns.factory.factory;

import com.designpatterns.factory.implementation.PushNotification;
import com.designpatterns.factory.model.Notification;

public class PushNotificationCreator extends NotificationCreator {

    @Override
    public Notification createNotification() {
        return new PushNotification();
    }
}
