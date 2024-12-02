package org.example.backmobile.Datas.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.EqualsAndHashCode;
import org.example.backmobile.Datas.Enums.TransactionType;
import prog.dependancy.Datas.Entity.EntityAbstract;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "scheduled_transfer")
@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
public class ScheduledTransfer  extends EntityAbstract {
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId", nullable = false)
    private User sender;

    @ElementCollection
    private List<String> contacts; // Liste de téléphones des contacts

    private Float montant;

    private TransactionType type;

    private String frequency;

    private int intervalDays;

    private LocalDateTime nextExecution; // Date de la prochaine exécution

    // Statut du transfert (actif ou annulé)
    private boolean isActive = true;
}
