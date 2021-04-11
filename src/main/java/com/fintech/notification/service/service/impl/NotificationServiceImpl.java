package com.fintech.notification.service.service.impl;

import com.fintech.notification.lib.model.request.EmailNotificationRequest;
import com.fintech.notification.service.service.EmailClientService;
import com.fintech.notification.service.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Executor EXECUTOR = Executors.newFixedThreadPool(20);
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class.getName());

    @Autowired
    EmailClientService emailClientService;

    @Override
    public void sendEmail(EmailNotificationRequest emailNotificationRequest) {
        EXECUTOR.execute(()-> {
            try {
                emailClientService.sendEmailToEmailProvider(emailNotificationRequest);
            } catch (Exception e) {
                LOGGER.error("Error occurred while sending mail due to ", e);
            }
        });
    }
}
