package com.assessment.javachatserver.service;


import com.assessment.javachatserver.domain.ChatMessage;
import com.assessment.javachatserver.domain.ChatRoom;
import com.assessment.javachatserver.domain.ChatUser;
import com.assessment.javachatserver.repository.ChatMessageRepository;
import com.assessment.javachatserver.repository.ChatRoomRepository;
import com.assessment.javachatserver.repository.ChatUserRepository;
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

    @Autowired
    private ChatUserRepository chatUserRepository;


    public ChatMessage createMessages(ChatMessage chatMessage){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByName("test");
        chatMessage.setChatRoom(chatRoom);

        ChatUser chatUser = chatUserRepository.findByUsername("test");
        chatMessage.setChatUser(chatUser);

        ChatMessage chatMessageSaved = chatMessageRepository.save(chatMessage);
        return chatMessageSaved;
    }

    public List<ChatMessage> getMessages(){
        List<ChatMessage> messages = chatMessageRepository.findAll();
        return messages;

    }

    public List<ChatMessage> getChatHistory(String chatRoomName)
    {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByName(chatRoomName);
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByChatRoom(chatRoom);
        return chatMessages;
    }

    public void getDeleteChatMessage(Long id)
    {
        ChatMessage chatMessage = chatMessageRepository.findChatMessagesById(id);
        chatMessageRepository.delete(chatMessage);
    }



}