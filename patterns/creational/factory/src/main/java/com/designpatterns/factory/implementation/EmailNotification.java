package com.designpatterns.factory.implementation;

import com.designpatterns.factory.model.Notification;

public class EmailNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Sending email Notification: " + message);
    }
}
