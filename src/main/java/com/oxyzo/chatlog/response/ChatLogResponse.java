package com.oxyzo.chatlog.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatLogResponse<T> {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String WARNING = "WARNING";
    
    private final String status;
    private final String message;
    private final T data;

    public ChatLogResponse() {
        this(SUCCESS, SUCCESS, null);
    }

    public ChatLogResponse(T data) {
        this(SUCCESS, SUCCESS, data);
    }

    public ChatLogResponse(String status, String message, T data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
