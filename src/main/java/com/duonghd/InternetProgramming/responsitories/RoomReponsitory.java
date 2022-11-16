package com.duonghd.InternetProgramming.responsitories;

import com.duonghd.InternetProgramming.entity.Account.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomReponsitory extends JpaRepository<Room, String> {
}
