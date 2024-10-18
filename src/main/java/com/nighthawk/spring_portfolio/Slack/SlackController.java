package com.nighthawk.spring_portfolio.Slack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class SlackController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/slack/events")
    public ResponseEntity<String> handleSlackEvent(@RequestBody SlackEvent payload) {
        // Check if this is a challenge request
        if (payload.getChallenge() != null) {
            return ResponseEntity.ok(payload.getChallenge());
        }

        try {
            SlackEvent.Event messageEvent = payload.getEvent();
            String eventType = messageEvent.getType();

            if ("message".equals(eventType)) {
                // Convert the message event to a JSON string
                ObjectMapper objectMapper = new ObjectMapper();
                String messageContent = objectMapper.writeValueAsString(messageEvent);

                // Save the message content using MessageService
                messageService.saveMessage(messageContent);

                System.out.println("Message saved to database: " + messageContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("OK");
    }
}
