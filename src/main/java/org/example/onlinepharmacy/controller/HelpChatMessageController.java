package org.example.onlinepharmacy.controller;

import org.example.onlinepharmacy.domain.HelpChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class HelpChatMessageController {

    private  final SimpMessagingTemplate template;

    public HelpChatMessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload HelpChatMessage chatMessage) {
        if ("admin".equals(chatMessage.getSender())) {
            template.convertAndSendToUser(chatMessage.getRecipient(),
                    "/queue/messages", chatMessage);
        } else {
            template.convertAndSend(
                    "/queue/admin", chatMessage);
        }
    }
}
