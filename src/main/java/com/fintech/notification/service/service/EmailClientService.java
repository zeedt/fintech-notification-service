package com.fintech.notification.service.service;

import com.fintech.notification.lib.model.request.EmailNotificationRequest;

public interface EmailClientService {
    void sendEmailToEmailProvider(EmailNotificationRequest emailNotificationRequest);
}
