package com.assessment.javachatserver;

import com.assessment.javachatserver.domain.ChatMessage;
import com.assessment.javachatserver.domain.ChatRoom;
import com.assessment.javachatserver.domain.ChatUser;
import com.assessment.javachatserver.repository.ChatMessageRepository;
import com.assessment.javachatserver.repository.ChatRoomRepository;
import com.assessment.javachatserver.repository.ChatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Override
    public void run(String... args) throws Exception {
        // Check if data is already initialized to prevent duplication
        if (chatUserRepository.count() == 0) {
            // Create and save dummy users
            ChatUser chatUser1 = new ChatUser();
            chatUser1.setUsername("test");
            chatUser1.setPassword("test");
            chatUserRepository.save(chatUser1);

        } else {
            System.out.println("Database already initialized");
        }


            // Create and save dummy users
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setName("test");
            chatRoomRepository.save(chatRoom);


        if (chatMessageRepository.count() == 0) {
            // Create and save dummy users
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setContent("test");
            chatMessageRepository.save(chatMessage);
        } else {
            System.out.println("Chat Message Database already initialized");
        }
    }
}
