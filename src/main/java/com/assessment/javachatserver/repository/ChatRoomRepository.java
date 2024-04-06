package com.assessment.javachatserver.repository;


import com.assessment.javachatserver.domain.ChatMessage;
import com.assessment.javachatserver.domain.ChatRoom;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {}