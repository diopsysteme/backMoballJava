//package org.example.backmobile.Datas.Repository;
//
//import org.SchoolApp.Datas.Entity.EmargementEntity;
//import org.SchoolApp.Datas.Entity.UserEntity;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.List;
//@Repository
//public interface EmargementRepository extends SoftDeleteRepository<EmargementEntity ,Long> {
//    //emargement date
//    EmargementEntity findByUserAndDate(UserEntity user, LocalDate date);
//
//    // Retrieve absence of a user
//    EmargementEntity findByUserAndEntreeIsNotNullAndSortieIsNotNullAndDate(UserEntity user, LocalDate date);
//
//    // Retrieve absence by date
//    EmargementEntity findByUserAndEntreeIsNullOrSortieIsNullAndDate(UserEntity user, LocalDate date);
//
//    // presences between two dates
//    List<EmargementEntity> findByUserAndEntreeIsNotNullAndSortieIsNotNullAndDateBetween(UserEntity user, LocalDate startDate, LocalDate endDate);
//
//    // emargement between two dates
//    List<EmargementEntity> findByUserAndDateBetween(UserEntity user, LocalDate startDate, LocalDate endDate);
//
//    // absences by month
//    List<EmargementEntity> findByUserAndDateBetweenAndEntreeIsNullOrSortieIsNull(UserEntity user, LocalDate startDate, LocalDate endDate);
//
//    // All absences
//    List<EmargementEntity> findByUserAndEntreeIsNullOrSortieIsNull(UserEntity user);
//
//    // Retrieve all presences of a user (Entree and Sortie not null)
//    List<EmargementEntity> findByUserAndEntreeIsNotNullAndSortieIsNotNull(UserEntity user);
//
//    // All emargements
//    List<EmargementEntity> findByUser(UserEntity user);
//
//
//
//
//}
