package com.duonghd.InternetProgramming.entity.Account;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="message")
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long idMessage;
    private String context;
    private Date timeSending;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sendAccount")
    private Account account;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idRoom")
    private Room room;


}
