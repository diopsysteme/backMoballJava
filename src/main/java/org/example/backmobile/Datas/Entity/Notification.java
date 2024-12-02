package org.example.backmobile.Datas.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import prog.dependancy.Datas.Entity.EntityAbstract;
import lombok.*;
import java.time.LocalDateTime;
@EqualsAndHashCode(callSuper = true)
@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
@Entity
@Table(name = "notification")
public class Notification extends EntityAbstract {
    private String message;
    private LocalDateTime readAt;
    @Column(nullable = false)
    private Boolean readed = false;
    @Column(nullable = false)
    private LocalDateTime date;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
