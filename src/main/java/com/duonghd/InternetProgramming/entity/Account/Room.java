package com.duonghd.InternetProgramming.entity.Account;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="room")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"idRoom"})
public class Room {
//    id room use to topic room
    @Id
    private String idRoom;

    private String context;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Job> jobs;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Message> messages;

    @PrePersist
    private void prePersist() {
        this.idRoom = UUID.randomUUID().toString();
    }
}
