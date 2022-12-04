package com.duonghd.InternetProgramming.responsitories;

import com.duonghd.InternetProgramming.entity.Account.Account;
import com.duonghd.InternetProgramming.entity.Account.Job;
import com.duonghd.InternetProgramming.entity.Account.Member;
import com.duonghd.InternetProgramming.entity.Account.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberReponsitory extends JpaRepository<Member, String> {
    Member findByAccountAndJob(Account account, Job job );
    List<Member> findByAccount(Account account);
    Member findByJob(Job job);

}
