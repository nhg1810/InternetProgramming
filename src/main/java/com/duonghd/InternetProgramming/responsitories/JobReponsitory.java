package com.duonghd.InternetProgramming.responsitories;
import com.duonghd.InternetProgramming.entity.Account.Account;
import com.duonghd.InternetProgramming.entity.Account.Job;
import com.duonghd.InternetProgramming.entity.Account.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobReponsitory extends JpaRepository<Job, Long> {
    List<Job> findByAccount(Account account);
    Job findByRoom(Room room);
    Job findByIdJobAndAccount(long idJob, Account account);
}
