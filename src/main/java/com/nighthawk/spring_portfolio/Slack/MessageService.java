package com.nighthawk.spring_portfolio.Slack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private SlackMessageRepository messageRepository;

    public void saveMessage(String channel, String user, String text) {
        // Create a new Message entity
        SlackMessage message = new SlackMessage(channel, user, text);
        // Save to the database
        messageRepository.save(message);
    }
}
