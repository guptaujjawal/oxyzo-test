package com.oxyzo.chatlog.service;

import com.oxyzo.chatlog.domain.Message;
import com.oxyzo.chatlog.domain.User;
import com.oxyzo.chatlog.dto.MessageDto;
import com.oxyzo.chatlog.dto.UserDto;
import com.oxyzo.chatlog.exception.ChatLogException;
import com.oxyzo.chatlog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Chat Log Server
 */
@Slf4j
@Service
public class ChatLogService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto saveUserChatLog(String userId, MessageDto messageDto)  throws ChatLogException {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user;
        List<Message> messages;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            messages = user.getMessages();
        } else {
            user = new User();
            user.setId(userId);
            messages = new ArrayList<>();
        }
        Message message = new Message(messageDto.getMessage(), messageDto.getTimeStamp().getEpochSecond(),
                messageDto.getIsSent());
        messages.add(message);
        user.setMessages(messages);
        userRepository.save(user);
        Optional<User> userAfterSave = userRepository.findById(userId);
        return mapUserToUserDto(userAfterSave);
    }

    public UserDto getUserChatLog(String userId, Integer limit, Long messageId)  throws ChatLogException {
        Optional<User> optionalUser = userRepository.findById(userId);
        UserDto dto = mapUserToUserDto(optionalUser);
        dto.getMessageDtoList().sort(Comparator.comparing(MessageDto::getTimeStamp).reversed());
        if(messageId != null) {
            int index = 0;
            for(int i = 0; i < dto.getMessageDtoList().size(); i++) {
                if(messageId == dto.getMessageDtoList().get(i).getMessageId()) {
                    index = i;
                    break;
                }
            }
            dto.setMessageDtoList(dto.getMessageDtoList().subList(index, dto.getMessageDtoList().size()));
        }
        if(limit == null) {
            limit = Integer.valueOf(10);
        }
        dto.setMessageDtoList(dto.getMessageDtoList().stream().limit(limit).collect(Collectors.toList()));
        return dto;
    }

    public Integer deleteUserAllChatLog(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Integer deletedRows = 0;
        if(optionalUser.isPresent()){
            deletedRows = optionalUser.get().getMessages().size();
            optionalUser.get().getMessages().clear();
            userRepository.save(optionalUser.get());
        }
        return deletedRows;
    }

    public Integer deleteUserChatLog(String userId, Long messageId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Integer deletedRows = 0;
        if(optionalUser.isPresent()){
            List<Message> messages = optionalUser.get().getMessages().stream()
                    .filter( m -> !messageId.equals(m.getMessageId())).collect(Collectors.toList());
            optionalUser.get().getMessages().clear();
            optionalUser.get().getMessages().addAll(messages);
            userRepository.save(optionalUser.get());
            deletedRows = 1;
        }
        return deletedRows;
    }

    private UserDto mapUserToUserDto(Optional<User> user) throws ChatLogException {
        if (user.isPresent()) {
            UserDto result = user.stream().map( u -> {
                UserDto dto = new UserDto();
                dto.setId(u.getId());
                List<MessageDto> messageDtos = u.getMessages().stream().map(m -> {
                    MessageDto mDto = new MessageDto(m.getMessageId(), m.getMessage(), Instant.ofEpochMilli(m.getTimeStamp()),
                            m.getIsSent());
                    return mDto;
                }).collect(Collectors.toList());
                dto.setMessageDtoList(messageDtos);
                return dto;
            }).findAny().get();
            return result;
        } else {
            throw new ChatLogException("User not found");
        }
    }
}
