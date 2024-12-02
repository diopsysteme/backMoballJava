//package org.example.backmobile.Datas.Entity;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import org.example.backmobile.Datas.Enums.UserType;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import prog.dependancy.Datas.Entity.EntityAbstract;
//
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//
//@Data
//@Entity
//@ToString
//public class UserEntity extends EntityAbstract implements UserDetails {
//
//    private String nom;
//    private String prenom;
//    private String adresse;
//    @Column(unique = true, nullable = false)
//    private String telephone;
//    @Column(unique = true, nullable = false)
//    private String email;
//    private String password;
//    @Enumerated(EnumType.STRING)
//    private String photo;
//    private UserType type;
//
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        UserEntity other = (UserEntity) obj;
//        return id != null && id.equals(other.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31; // or use a constant or just return a unique identifier hash
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Convert the Role object to a SimpleGrantedAuthority and wrap it in a list
//        return Collections.singletonList(new SimpleGrantedAuthority(type.toString()));
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//
//
//}
