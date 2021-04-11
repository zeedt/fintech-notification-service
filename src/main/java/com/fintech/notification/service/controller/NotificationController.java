package com.fintech.notification.service.controller;


import com.fintech.notification.lib.model.request.EmailNotificationRequest;
import com.fintech.notification.service.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class.getName());

    @PostMapping("/sendEmail")
    public void sendEmailNotification(@RequestBody @Validated EmailNotificationRequest emailNotificationRequest) {
        try {
            notificationService.sendEmail(emailNotificationRequest);
        } catch (Exception e) {
            LOGGER.error("Error occurred due to ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to complete email request at this time");
        }
    }

}
