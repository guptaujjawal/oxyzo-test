package com.oxyzo.chatlog.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ChatLogListResponse<T> {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String WARNING = "WARNING";

    private final String status;
    private final String message;
    private final List<T> data;

    public ChatLogListResponse() {
        this(SUCCESS, SUCCESS, null);
    }

    public ChatLogListResponse(List<T> data) {
        this(SUCCESS, SUCCESS, data);
    }

    public ChatLogListResponse(String status, String message, List<T> data) {
        super();
        this.status = status;
        this.message = message;
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<T> getData() {
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
