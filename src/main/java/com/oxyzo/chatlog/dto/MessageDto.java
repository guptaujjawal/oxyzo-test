package com.oxyzo.chatlog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class MessageDto {

    private Long messageId;
    private String message;
    private Instant timeStamp;
    private Boolean isSent;
}
