package com.duonghd.InternetProgramming.entity.Account;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="detail-job")
@EqualsAndHashCode(of = {"idDetailJob"})
@AllArgsConstructor
@NoArgsConstructor
public class DetailJob {
    @Id
    private String idDetailJob;
    private String descripts;
    private String skillApply;
    private String level;
    private String from;
    private String language;
    private Date date;

    @OneToOne
    @JoinColumn(name = "fk_detail_job")
    private Job job;

    @PrePersist
    private void prePersist() {
        this.idDetailJob = UUID.randomUUID().toString();
    }
}
