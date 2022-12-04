package com.duonghd.InternetProgramming.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name="moreAccount")
@NoArgsConstructor
@AllArgsConstructor
public class MoreInfAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMAcount;
    private String phone;
    private Date birth;
//    @NotEmpty(message = "{NotEmpty.address}")
    private String address;
//    @NotEmpty(message = "{NotEmpty.descriptGraduation}")
    private String descriptGraduation;

    private String avata;
//    @NotEmpty(message = "{NotEmpty.link}")
    private String link;

//    @NotEmpty(message = "{NotEmpty.experience}")
    private String experience;

    private String context;

    private String myBlog;

    @OneToOne
    @JoinColumn(name = "fk_account")
    private Account account;
}
