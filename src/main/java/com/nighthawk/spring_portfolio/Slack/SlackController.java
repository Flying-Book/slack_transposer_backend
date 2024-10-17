package com.nighthawk.spring_portfolio.Slack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlackController {

    @Autowired
    private SlackMessageRepository messageRepository;

    @PostMapping("/slack/events")
    public ResponseEntity<String> handleSlackEvent(@RequestBody SlackEvent payload) {
        // Check if this is a challenge request
        if (payload.getChallenge() != null) {
            return ResponseEntity.ok(payload.getChallenge());
        }

        try {
            String eventType = payload.getEvent().getType();

            if ("message".equals(eventType)) {
                // Extract message details
                String userId = payload.getEvent().getUser();
                String text = payload.getEvent().getText();
                String channelId = payload.getEvent().getChannel();

                // Create a new SlackMessage object
                SlackMessage message = new SlackMessage();
                message.setUserId(userId);
                message.setText(text);
                message.setChannelId(channelId);

                // Save to the database
                messageRepository.save(message);
                System.out.println("Message saved to database: " + message.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("OK");
    }
}
