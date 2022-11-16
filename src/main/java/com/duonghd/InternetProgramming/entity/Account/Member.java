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
    @JoinColumn(name = "idMember1")
    private Account account1;

    @ManyToOne
    @JoinColumn(name = "idMember2")
    private Account account2;

    @ManyToOne
    @JoinColumn(name = "idMember3")
    private Account account3;

    @ManyToOne
    @JoinColumn(name = "idMember4")
    private Account account4;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Job> jobs;

    @PrePersist
    private void prePersist() {
        this.idMb = UUID.randomUUID().toString();
    }


}
