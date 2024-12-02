package org.example.backmobile.Datas.Repository;


import org.example.backmobile.Datas.Entity.User;
import org.springframework.stereotype.Repository;
import prog.dependancy.Datas.Repository.SoftDeleteRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends SoftDeleteRepository<User, Long> {

    // Trouver un utilisateur par email
    Optional<User> findByMail(String email);
    Optional<User> findByTelephone(String telephone);

}
