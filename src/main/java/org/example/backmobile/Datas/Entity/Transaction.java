package org.example.backmobile.Datas.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import lombok.EqualsAndHashCode;
import org.example.backmobile.Datas.Enums.TransactionType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import prog.dependancy.Datas.Entity.EntityAbstract;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transaction")
@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor
@ToString(exclude = {"sender", "receiver"})
public class Transaction extends EntityAbstract {
    private Float montant;

    private String status;

    private LocalDateTime date;

    private Float soldeSender;

    private Float soldeReceiver;

    private Float frais;

    private TransactionType type;

    @JsonIgnore // Add this
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId")
    private User sender;

    @JsonIgnore // Add this
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverId")
    private User receiver;

    private String receiverString;
}
