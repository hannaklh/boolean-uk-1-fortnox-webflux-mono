package org.booleanuk.demo.controller;

import org.booleanuk.demo.model.dto.*;
import org.booleanuk.demo.model.service.MessageService;
import org.booleanuk.demo.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //mono

    @GetMapping("users/{id}/listen")
    public Mono<ResponseEntity<MessageResponseDto>> listenForNewMessages(@PathVariable long id) {
        LocalDateTime lastTimeChecked = LocalDateTime.now();
        return Mono.fromCallable(() -> userService.getById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(u -> {
                    if (u == null) {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                    return checkMessages(id, lastTimeChecked)
                            .timeout(Duration.ofMinutes(2))
                            .map(ResponseEntity::ok);
                });
    }

    private Mono<MessageResponseDto> checkMessages(long id, LocalDateTime lastTimeChecked) {

        return Mono.fromCallable(() -> userService.getById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(u -> {

                    Optional<MessageResponseDto> newMessage = u.getMessageList().stream()
                            .filter(m -> m.getDate().isAfter(lastTimeChecked))
                            .findFirst();

                    if (newMessage.isPresent()) {
                        // [!note] Check if `Restaurant` is `open` or not
                        return Mono.just(newMessage.get());
                    }// [!note] It is, so get back with data
                    else  // [!note] It's not, so let's try again in 1 second
                    {
                        return Mono.delay(Duration.ofSeconds(1))
                                .then(checkMessages(id, lastTimeChecked));  // [!note] Recursive call to `pollUntilOpen` method

                    }
                });
    }
}




