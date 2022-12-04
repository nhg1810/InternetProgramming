package com.duonghd.InternetProgramming.entity.Account;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="member")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"idMb"})
public class Member {
    @Id
    private String idMb;

    @ManyToOne
    @JoinColumn(name = "idMember")
    private Account account;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idJob")
    private Job job;

    private int status;

    @PrePersist
    private void prePersist() {
        this.idMb = UUID.randomUUID().toString();
    }


}
