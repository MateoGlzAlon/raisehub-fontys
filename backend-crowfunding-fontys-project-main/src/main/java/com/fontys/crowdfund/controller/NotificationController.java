package com.fontys.crowdfund.controller;

import com.fontys.crowdfund.persistence.websocketnotification.NotificationMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface NotificationController {
    ResponseEntity<Void> sendNotificationToUsers(@RequestBody NotificationMessage message);
}
