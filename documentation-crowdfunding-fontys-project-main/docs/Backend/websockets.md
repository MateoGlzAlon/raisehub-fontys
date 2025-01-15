---
sidebar_position: 9
sidebar_label: Websockets
---

# WebSockets implementation

This documentation outlines how WebSocket communication is used to manage project notifications in the application in the Back-end side.

[Front-end websockets implementation](../Frontend/websockets.md)

## **Dependency Setup**

Add the required WebSocket dependency in your `build.gradle` file:

```groovy
implementation 'org.springframework.boot:spring-boot-starter-websocket'
```

---

## **Configuration**

Create a WebSocket configuration file to define the topics and endpoints for your WebSocket connections. Below is an example configuration:

```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple message broker for topic destinations
        config.enableSimpleBroker("/projectws");
        // Define a user destination prefix
        config.setUserDestinationPrefix("/projectws");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the WebSocket endpoint and allow cross-origin requests
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000");
    }
}
```

---

## **Controller for Notifications**

To enable the backend to send notifications through WebSockets, a controller can be created. Below is an example implementation:

```java
@RestController
@AllArgsConstructor
@RequestMapping("notifications")
public class NotificationsController {

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<Void> sendNotificationToUsers(@RequestBody NotificationMessage message) {
        // Publish the message to the "/project/payment" topic
        messagingTemplate.convertAndSend("/project/payment", message);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
```

---

### **Key Components Explained**

1. **WebSocket Configuration:**
   - `configureMessageBroker`: Configures the message broker to handle destinations prefixed with `/projectws`.
   - `registerStompEndpoints`: Defines `/ws` as the endpoint for WebSocket connections and allows cross-origin requests from `http://localhost:3000`.

2. **SimpMessagingTemplate:**
   - Used to send messages to WebSocket destinations.
   - In the example above, messages are sent to the `/project/payment` topic.

3. **REST Endpoint for Notifications:**
   - The `sendNotificationToUsers` method accepts `NotificationMessage` payloads via a `POST` request and broadcasts them to the topic `/project/payment`.