package com.duonghd.InternetProgramming.responsitories;

import com.duonghd.InternetProgramming.entity.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountResponeitory extends JpaRepository<Account, Long> {
    Account findByEmailAndPassWord(String email, String password);
    Account findByEmail(String email);
}
