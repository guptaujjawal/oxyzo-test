package com.oxyzo.chatlog.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String id;
    List<MessageDto> messageDtoList;
}
