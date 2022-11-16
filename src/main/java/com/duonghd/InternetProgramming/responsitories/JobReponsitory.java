package com.duonghd.InternetProgramming.responsitories;
import com.duonghd.InternetProgramming.entity.Account.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobReponsitory extends JpaRepository<Job, Long> {
}
