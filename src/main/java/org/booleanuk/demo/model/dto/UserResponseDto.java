package org.booleanuk.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.booleanuk.demo.model.jpa.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private long id;
    private String username;

    public UserResponseDto(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
    }
}
