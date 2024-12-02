package org.example.backmobile.Datas.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.backmobile.Datas.Enums.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import prog.dependancy.Datas.Entity.EntityAbstract;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor


@Entity
@Table(name = "\"user\"")
@ToString(exclude = {"sentTransaction", "receivedTransaction", "notifications", "contacts"})
public class User extends EntityAbstract implements UserDetails {
    private String nom;
    @Column(unique = true, nullable = false)
    private String identifiant;

    private String prenom;
    @Column(nullable = true)
    private String qr;
    @Column(unique = true, nullable = false)
    private String mail;

    private String codeSecret;

    @Column(unique = true)
    private String telephone;

    private Float solde;

    private Float plafond;

    private String type;
    @JsonIgnore // Add this
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Change to LAZY
    private List<Notification> notifications;

    @JsonIgnore // Add this
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Change to LAZY
    private List<Contact> contacts;

    @JsonIgnore // Add this
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Change to LAZY
    private List<Transaction> sentTransaction;

    @JsonIgnore // Add this
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Change to LAZY
    private List<Transaction> receivedTransaction;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() {
        return 31; // or use a constant or just return a unique identifier hash
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert the Role object to a SimpleGrantedAuthority and wrap it in a list
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+type.toString()));
    }

    @Override
    public String getPassword() {
        return codeSecret;
    }
    @Override
    public String getUsername() {
        return telephone;
    }

}
