package com.assessment.javachatserver.service;


import com.assessment.javachatserver.domain.ChatMessage;
import com.assessment.javachatserver.repository.ChatMessageRepository;
import com.assessment.javachatserver.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;


    public ChatMessage createMessages(ChatMessage chatMessage){
        ChatMessage chatMessageSaved = chatMessageRepository.save(chatMessage);
        return chatMessageSaved;
    }

    public List<ChatMessage> getMessages(){
        List<ChatMessage> messages = chatMessageRepository.findAll();
        return messages;

    }

    public List<ChatMessage> getChatHistory(Long chatRoomId)
    {
        AtomicReference<List<ChatMessage>> messages = new AtomicReference<>(new ArrayList<ChatMessage>());
        chatRoomRepository.findById(chatRoomId)
                .ifPresent(chatRoom -> {
                    messages.set(chatMessageRepository.findChatMessagesByChatRoom(chatRoom));
                });

        return messages.get();
    }



}