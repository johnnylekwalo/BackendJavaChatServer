package com.assessment.javachatserver.service;


import com.assessment.javachatserver.domain.ChatMessage;
import com.assessment.javachatserver.domain.ChatUser;
import com.assessment.javachatserver.repository.ChatMessageRepository;
import com.assessment.javachatserver.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public interface ChatUserService {
    /**
     * Save a chatUser.
     *
     * @param chatUser the entity to save.
     * @return the persisted entity.
     */
    ChatUser save(ChatUser chatUser);

    /**
     * Updates a chatUser.
     *
     * @param chatUser the entity to update.
     * @return the persisted entity.
     */
    ChatUser update(ChatUser chatUser);

    /**
     * Partially updates a chatUser.
     *
     * @param chatUser the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChatUser> partialUpdate(ChatUser chatUser);

    /**
     * Get all the chatUsers.
     *
     * @return the list of entities.
     */
    List<ChatUser> findAll();

    /**
     * Get the "id" chatUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChatUser> findOne(Long id);

    /**
     * Delete the "id" chatUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
