package org.booleanuk.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.booleanuk.demo.model.jpa.Message;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
    private long id;
    private String text;
    private LocalDateTime date;
    private UserResponseDto user;

    public MessageResponseDto(Message message) {
        setId(message.getId());
        setText(message.getText());
        setDate(message.getDate());
        if (message.getUser() != null) {
            setUser(new UserResponseDto(message.getUser()));
        }
    }

}
