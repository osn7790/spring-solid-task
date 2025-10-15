package com.puzzlix.solid_task.domain.notification;

public class EmailNotificationSender implements NotificationSender{

    @Override
    public void send(String message) {

    }

    @Override
    public boolean supports(String type) {
        return false;
    }
}
