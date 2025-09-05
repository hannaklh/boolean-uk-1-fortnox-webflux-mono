package org.booleanuk.demo.controller;

import org.booleanuk.demo.model.dto.*;
import org.booleanuk.demo.model.service.MessageService;
import org.booleanuk.demo.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserMessagesController {
    private final UserService userService;
    private final MessageService messageService;

    public UserMessagesController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("users")
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }
    @GetMapping("users/{id}")
    public ResponseEntity<UserResponseWithMessagesDto> getUserById(@PathVariable long id) throws Exception {
        UserResponseWithMessagesDto user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("users")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userDto) {
        UserResponseDto user = userService.createUser(userDto);
        return ResponseEntity.ok(user);
    }
    @PutMapping("users/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable long id, @RequestBody UserRequestDto userRequestDto) throws Exception {
        UserResponseDto user = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws Exception {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("send-message")
    public ResponseEntity<MessageResponseDto> sendMessage(@RequestBody MessageRequestDto messageRequestDto) throws Exception {
        MessageResponseDto message = messageService.sendMessage(messageRequestDto);
        return ResponseEntity.ok(message);
    }
}
