package com.fontys.crowdfund.persistence.websocketnotification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessage {
    private String id;
    private String from;
    private String to;
    private String text;
}
