package com.assessment.javachatserver.service;


import com.assessment.javachatserver.domain.ChatUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserDetailsService {

    UserDetails loadUserByUsername(String username);
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
