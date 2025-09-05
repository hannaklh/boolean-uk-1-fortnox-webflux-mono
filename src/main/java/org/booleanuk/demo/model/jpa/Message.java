package org.booleanuk.demo.model.jpa;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.booleanuk.demo.model.dto.MessageRequestDto;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "text")
    private String text;
    @Column(name = "date")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    public Message(MessageRequestDto messageRequestDto) {
        setText(messageRequestDto.getText());
    }
}
