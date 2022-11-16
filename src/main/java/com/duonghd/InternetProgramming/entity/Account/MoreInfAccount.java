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
public class MoreInfAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMAcount;
    private String phone;
    private Date birth;
    @NotEmpty(message = "{NotEmpty.address}")
    private String address;
    @NotEmpty(message = "{NotEmpty.descriptGraduation}")
    private String descriptGraduation;

    private String avata;
    @NotEmpty(message = "{NotEmpty.link}")
    private String link;

    @NotEmpty(message = "{NotEmpty.experience}")
    private String experience;

    private String context;

    public MoreInfAccount(String phone, Date birth, String address, String descriptGraduation, String avata, String link, String experience, String context, Account account) {
        this.phone = phone;
        this.birth = birth;
        this.address = address;
        this.descriptGraduation = descriptGraduation;
        this.avata = avata;
        this.link = link;
        this.experience = experience;
        this.context = context;
        this.account = account;
    }

    @OneToOne
    @JoinColumn(name = "fk_account")
    private Account account;
}
