package com.oxyzo.chatlog.exception;

public class ChatLogException extends Exception {

    public ChatLogException() {
        super();
    }

    public ChatLogException(String message) {
        super(message);
    }

    public ChatLogException(Throwable throwable) {
        super(throwable);
    }

    public ChatLogException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
