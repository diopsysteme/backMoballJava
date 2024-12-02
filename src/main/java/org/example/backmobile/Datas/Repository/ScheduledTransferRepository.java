    package org.example.backmobile.Datas.Repository;

import org.example.backmobile.Datas.Entity.ScheduledTransfer;
import org.example.backmobile.Datas.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import prog.dependancy.Datas.Repository.SoftDeleteRepository;

import java.time.LocalDateTime;
import java.util.List;

    @Repository
public interface ScheduledTransferRepository extends SoftDeleteRepository<ScheduledTransfer,Long> {
    List<ScheduledTransfer> findByNextExecutionBeforeAndIsActive(LocalDateTime now, boolean b);

        Page<ScheduledTransfer> findBySenderAndIsActive(User user, boolean b, Pageable pageable);
    }
