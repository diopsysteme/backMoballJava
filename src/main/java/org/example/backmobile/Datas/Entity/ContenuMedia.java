package org.example.backmobile.Datas.Entity;

import jakarta.persistence.*;
import lombok.*;
import prog.dependancy.Datas.Entity.EntityAbstract;

import javax.print.attribute.standard.Media;
@Entity
@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
@Table(name = "contenu_media")
public class ContenuMedia extends EntityAbstract {
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
}
