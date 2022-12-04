package com.duonghd.InternetProgramming.entity.Account;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long idAccount;
    @NotEmpty(message = "{NotEmpty.name}")
    private String name;
    @NotEmpty(message = "{NotEmpty.password}")
    private String passWord;
    @NotEmpty(message = "{NotEmpty.email}")
    @Email(message = "{Invalid.email}")
    private String email;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private MoreInfAccount moreInfAccount;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Job> jobs;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Member> member;
}
