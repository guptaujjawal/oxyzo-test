package com.oxyzo.chatlog.controller;

import com.oxyzo.chatlog.dto.MessageDto;
import com.oxyzo.chatlog.dto.UserDto;
import com.oxyzo.chatlog.response.ChatLogResponse;
import com.oxyzo.chatlog.service.ChatLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * HTTP Controller class for Chat Log Server
 */
@Slf4j
@RestController
@RequestMapping("/")
public class ChatLogController {

    private ChatLogService chatLogService;

    @Autowired
    public void setChatLogService(ChatLogService chatLogService) {
        this.chatLogService = chatLogService;
    }

    /**
     * Creates a new chat log entry for the user
     * @param userId
     * @param messageDto
     * @return Chat log response with message id
     */
    @PostMapping(value = "/chatlogs/{user}", produces = "application/json")
    public ChatLogResponse<UserDto> saveUserChatLog(@PathVariable("user") String userId,
                                                   @RequestBody MessageDto messageDto) {

        ChatLogResponse<UserDto> dataResponse;
        try {
            UserDto dto = chatLogService.saveUserChatLog(userId, messageDto);
            dataResponse = new ChatLogResponse<>(dto);
        } catch (Exception ex) {
            log.error("Failed to save user chat log", ex);
            dataResponse = new ChatLogResponse<>(ChatLogResponse.FAILURE, ex.getMessage(), null);
        }
        return dataResponse;
    }


    /**
     * Returns chat logs for the given user.
     * @param userId
     * @param limit How many messages should return. Default to 10
     * @param start messageID to determine where to start from
     * @return User with list of chat log
     */
    @GetMapping(value = "/chatlogs/{user}", produces = "application/json")
    public ChatLogResponse<UserDto> getUserChatLog(@PathVariable("user") String userId,
                                                   @RequestParam(required = false) Integer limit,
                                                   @RequestParam(required = false) Long start) {
        ChatLogResponse<UserDto> dataResponse;
        try {
            UserDto userDto = chatLogService.getUserChatLog(userId, limit, start);
            dataResponse = new ChatLogResponse<>(userDto);
        } catch (Exception ex) {
            log.error("Failed to get user chat log", ex);
            dataResponse = new ChatLogResponse<>(ChatLogResponse.FAILURE, ex.getMessage(), null);
        }
        return dataResponse;
    }

    /**
     * Deletes all the chat logs for a given user
     * @param userId
     * @return No of message deleted
     */
    @DeleteMapping(value = "/chatlogs/{user}", produces = "application/json")
    public ChatLogResponse<Integer> deleteUserAllChatLog(@PathVariable("user") String userId) {
        ChatLogResponse<Integer> dataResponse;
        try {
            Integer deletedRows = chatLogService.deleteUserAllChatLog(userId);
            dataResponse = new ChatLogResponse<>(deletedRows);
        } catch (Exception ex) {
            log.error("Failed to delete user all chat log", ex);
            dataResponse = new ChatLogResponse<>(ChatLogResponse.FAILURE, ex.getMessage(), null);
        }
        return dataResponse;
    }

    /**
     * Delete just the given chat log for a given user
     * @param userId
     * @param messageId
     * @return No of message deleted
     */
    @DeleteMapping(value = "/chatlogs/{user}/{msgid}", produces = "application/json")
    public ChatLogResponse<Integer> deleteUserChatLog(@PathVariable("user") String userId,
                                                      @PathVariable("msgid") Long messageId) {
        ChatLogResponse<Integer> dataResponse;
        try {
            Integer deletedRows = chatLogService.deleteUserChatLog(userId, messageId);
            dataResponse = new ChatLogResponse<>(deletedRows);
        } catch (Exception ex) {
            log.error("Failed to delete user chat log", ex);
            dataResponse = new ChatLogResponse<>(ChatLogResponse.FAILURE, ex.getMessage(), null);
        }
        return dataResponse;
    }


}
