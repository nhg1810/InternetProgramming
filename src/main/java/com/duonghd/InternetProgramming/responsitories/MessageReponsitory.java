package com.duonghd.InternetProgramming.responsitories;

import com.duonghd.InternetProgramming.entity.Account.Job;
import com.duonghd.InternetProgramming.entity.Account.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageReponsitory  extends JpaRepository<Message, Long> {
}
