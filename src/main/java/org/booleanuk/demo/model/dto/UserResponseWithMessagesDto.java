package org.booleanuk.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.booleanuk.demo.model.jpa.User;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseWithMessagesDto {
    private long id;
    private String username;
    private List<MessageResponseDto> messageList;

    public UserResponseWithMessagesDto(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
        setMessageList(user.getMessageList() != null ?
                user.getMessageList().stream()
                .map(MessageResponseDto::new)
                .toList():
                new ArrayList<>());
    }
}
