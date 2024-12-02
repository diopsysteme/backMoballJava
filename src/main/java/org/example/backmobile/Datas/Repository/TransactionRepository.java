package org.example.backmobile.Datas.Repository;

import org.example.backmobile.Datas.Entity.Transaction;
import org.example.backmobile.Datas.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prog.dependancy.Datas.Repository.SoftDeleteRepository;
@Repository
public interface TransactionRepository extends SoftDeleteRepository<Transaction,Long> {
    @Query("SELECT t FROM Transaction t WHERE (t.sender = :user OR t.receiver = :user)")
    Page<Transaction> findAllBySenderOrReceiver(@Param("user") User user, Pageable pageable);
}
