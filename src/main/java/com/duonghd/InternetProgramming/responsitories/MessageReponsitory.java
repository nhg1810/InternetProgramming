package com.duonghd.InternetProgramming.responsitories;

import com.duonghd.InternetProgramming.entity.Account.Job;
import com.duonghd.InternetProgramming.entity.Account.Message;
import com.duonghd.InternetProgramming.entity.Account.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageReponsitory  extends JpaRepository<Message, Long> {
    List<Message> findByRoom(Room room);
}
