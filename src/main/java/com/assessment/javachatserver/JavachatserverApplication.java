package com.assessment.javachatserver;

import com.assessment.javachatserver.domain.ChatRoom;
import com.assessment.javachatserver.repository.ChatRoomRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavachatserverApplication {

	private static final Logger log = LoggerFactory.getLogger(JavachatserverApplication.class);

	private final ChatRoomRepository chatRoomRepository;

	public JavachatserverApplication(ChatRoomRepository chatRoomRepository) {
		this.chatRoomRepository = chatRoomRepository;
	}

	@PostConstruct
	public void initApplication() {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setName("test dummy");
		chatRoomRepository.save(chatRoom);

	}

	public static void main(String[] args) {

		SpringApplication.run(JavachatserverApplication.class, args);



	}

}
