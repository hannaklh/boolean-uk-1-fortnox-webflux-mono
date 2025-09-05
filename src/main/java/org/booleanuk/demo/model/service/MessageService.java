package org.booleanuk.demo.model.service;

import org.booleanuk.demo.model.dto.*;
import org.booleanuk.demo.model.jpa.Message;
import org.booleanuk.demo.model.jpa.User;
import org.booleanuk.demo.model.repo.MessageRepository;
import org.booleanuk.demo.model.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public MessageResponseDto sendMessage(MessageRequestDto messageDto) throws Exception {

        User user = userRepository.findById(messageDto.getUser_id())
                .orElseThrow(() -> new Exception("Target user not found"));

        Message message = new Message(messageDto);
        message.setDate(LocalDateTime.now());
        message.setUser(user);
        messageRepository.save(message);
        return new MessageResponseDto(message);

    }

}
