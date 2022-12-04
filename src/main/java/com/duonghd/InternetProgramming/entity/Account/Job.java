package com.duonghd.InternetProgramming.entity.Account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Member> members;


    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private DetailJob detailJob;
}
