package com.duonghd.InternetProgramming.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="job")
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idJob")
    private long idJob;
    private String nameJob;
    private String context;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idRoom")
    private Room room;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idHost")
    private Account account;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idMember")
    private Member member;
}
