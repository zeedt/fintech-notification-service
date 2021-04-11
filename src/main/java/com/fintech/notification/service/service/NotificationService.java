package com.fintech.notification.service.service;

import com.fintech.notification.lib.model.request.EmailNotificationRequest;

public interface NotificationService {
    void sendEmail(EmailNotificationRequest emailNotificationRequest);
}
