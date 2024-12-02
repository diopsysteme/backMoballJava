package org.example.backmobile.Datas.Repository;

import org.example.backmobile.Datas.Entity.ContenuMedia;
import org.springframework.stereotype.Repository;
import prog.dependancy.Datas.Repository.SoftDeleteRepository;
@Repository
public interface ContenuMediaRepository extends SoftDeleteRepository<ContenuMedia, Long> {
}
