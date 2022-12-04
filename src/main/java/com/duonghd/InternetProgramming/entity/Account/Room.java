package com.duonghd.InternetProgramming.entity.Account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties
public class Room {
//    id room use to topic room
    @Id
    private String idRoom;

    private String context;
    private String tokenRoom;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("jobs")
    private List<Job> jobs;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("messages")
    private List<Message> messages;

    @Override
    public String toString() {
        return "Room{" +
                "idRoom='" + idRoom + '\'' +
                ", context='" + context + '\'' +
                ", jobs=" + jobs +
                ", messages=" + messages +
                '}';
    }

    @PrePersist
    private void prePersist() {
        this.idRoom = UUID.randomUUID().toString();
    }
}
