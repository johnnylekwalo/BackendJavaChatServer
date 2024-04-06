package com.assessment.javachatserver.web.rest;

import com.assessment.javachatserver.domain.ChatMessage;
import com.assessment.javachatserver.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Endpoint to send a message
    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessage chatMessage) {
        // Logic to handle sending a message
        ChatMessage chatMessageSent = chatService.createMessages(chatMessage);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> getMessages() {
        // Logic to handle sending a message
        List<ChatMessage> messages = chatService.getMessages();
        return ResponseEntity.ok(messages);
    }

    // Endpoint to get chat history for a room
    @GetMapping("/chat/{chatRoomId}/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable Long chatRoomId) {
        List<ChatMessage> history = chatService.getChatHistory(chatRoomId);
        return ResponseEntity.ok(history);
    }

    // Additional endpoints as needed
}
