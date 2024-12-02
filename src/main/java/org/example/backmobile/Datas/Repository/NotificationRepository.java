package org.example.backmobile.Datas.Repository;

import org.example.backmobile.Datas.Entity.Notification;
import org.springframework.stereotype.Repository;
import prog.dependancy.Datas.Repository.SoftDeleteRepository;
@Repository
public interface NotificationRepository extends SoftDeleteRepository<Notification,Long> {
}
