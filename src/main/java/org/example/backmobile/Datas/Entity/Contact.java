package org.example.backmobile.Datas.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.EqualsAndHashCode;
import prog.dependancy.Datas.Entity.EntityAbstract;
@EqualsAndHashCode(callSuper = true)
@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
@Entity
@Table(name = "contact")
public class Contact extends EntityAbstract {
    private String nom;

    private String prenom;

    private String telephone;
@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
