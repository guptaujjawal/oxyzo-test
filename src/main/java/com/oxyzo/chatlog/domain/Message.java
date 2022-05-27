package com.oxyzo.chatlog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

/**
 * Domain model for Chat Log message
 */
@Entity
@Table(name="messages")
@Getter
@Setter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;

    @Column(name="message", length=4000, nullable=false, unique=false)
    private String message;

    @Column(name="timestamp")
    private Long timeStamp;

    @Column(name = "isent", columnDefinition = "boolean default false", nullable=false)
    private Boolean isSent;

    public Message(String message, Long timeStamp, Boolean isSent) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.isSent = isSent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getMessageId().equals(message.getMessageId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessageId());
    }

}
