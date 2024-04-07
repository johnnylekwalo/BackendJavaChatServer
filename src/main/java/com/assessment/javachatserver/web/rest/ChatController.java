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
        ChatMessage chatMessageSent = chatService.createMessages(chatMessage);
        return ResponseEntity.ok(chatMessageSent);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> getMessages() {
        List<ChatMessage> messages = chatService.getMessages();
        return ResponseEntity.ok(messages);
    }

    // Endpoint to get chat history for a room
    @GetMapping("/chat/{chatRoomName}/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String chatRoomName) {
        List<ChatMessage> history = chatService.getChatHistory(chatRoomName);
        return ResponseEntity.ok(history);
    }

    // Additional endpoints as needed
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> getChatHistory(@PathVariable Long id) {
        chatService.getDeleteChatMessage(id);
        return ResponseEntity.noContent().build();
    }
}
