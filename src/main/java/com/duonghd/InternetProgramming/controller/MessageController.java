package com.duonghd.InternetProgramming.controller;

import com.duonghd.InternetProgramming.entity.Account.Account;
import com.duonghd.InternetProgramming.entity.Account.Message;
import com.duonghd.InternetProgramming.entity.Account.MessageModel;
import com.duonghd.InternetProgramming.entity.Account.Room;
import com.duonghd.InternetProgramming.responsitories.AccountResponeitory;
import com.duonghd.InternetProgramming.responsitories.MessageReponsitory;
import com.duonghd.InternetProgramming.responsitories.RoomReponsitory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController("/dashboard-chat")
public class MessageController {
    private AccountResponeitory accountResponeitory;
    private MessageReponsitory messageReponsitory;
    private RoomReponsitory roomReponsitory;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel message) {

            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        Long userSend = Long.parseLong(message.getFromLogin());
        String context = message.getMessage();
        Date date = new Date();

        Optional<Account> account = accountResponeitory.findById(userSend.longValue());
        Account accountSend = account.get();


        Optional<Room> room = roomReponsitory.findById(to.toString());
        Room roomSend = room.get();

        Message messageStore = new Message();
        messageStore.setContext(context);
        messageStore.setAccount(accountSend);
        messageStore.setRoom(roomSend);
        messageStore.setTimeSending(date);
        messageReponsitory.save(messageStore);
    }

//    public ResponseEntity<Room> showMessage(@PathVariable String idRoom){
//        Room room = roomReponsitory.getOne(idRoom);
//        if (room == null) {
//            return ResponseEntity.notFound().build();
//        } else {
//            return ResponseEntity.ok(room);
//        }
//    }
}
