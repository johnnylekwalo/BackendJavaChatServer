package com.assessment.javachatserver.service.impl;


import com.assessment.javachatserver.domain.ChatUser;
import com.assessment.javachatserver.repository.ChatUserRepository;
import com.assessment.javachatserver.service.ChatUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChatUserServiceImpl implements ChatUserService {

    private final Logger log = LoggerFactory.getLogger(ChatUserServiceImpl.class);

    private final ChatUserRepository chatUserRepository;

    public ChatUserServiceImpl(ChatUserRepository chatUserRepository) {
        this.chatUserRepository = chatUserRepository;
    }

    @Override
    public ChatUser save(ChatUser chatUser) {
        log.debug("Request to save ChatUser : {}", chatUser);
        return chatUserRepository.save(chatUser);
    }

    @Override
    public ChatUser update(ChatUser chatUser) {
        log.debug("Request to update ChatUser : {}", chatUser);
        return chatUserRepository.save(chatUser);
    }

    @Override
    public Optional<ChatUser> partialUpdate(ChatUser chatUser) {
        log.debug("Request to partially update ChatUser : {}", chatUser);

        return chatUserRepository
                .findById(chatUser.getId())
                .map(existingChatUser -> {
                    if (chatUser.getUsername() != null) {
                        existingChatUser.setUsername(chatUser.getUsername());
                    }
                    if (chatUser.getPassword() != null) {
                        existingChatUser.setPassword(chatUser.getPassword());
                    }

                    return existingChatUser;
                })
                .map(chatUserRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatUser> findAll() {
        log.debug("Request to get all ChatUsers");
        return chatUserRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChatUser> findOne(Long id) {
        log.debug("Request to get ChatUser : {}", id);
        return chatUserRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChatUser : {}", id);
        chatUserRepository.deleteById(id);
    }
}
