package org.example.backmobile.Datas.Repository;

import org.example.backmobile.Datas.Entity.Contact;
import org.springframework.stereotype.Repository;
import prog.dependancy.Datas.Repository.SoftDeleteRepository;
@Repository
public interface ContactRepository extends SoftDeleteRepository<Contact,Long> {
}
