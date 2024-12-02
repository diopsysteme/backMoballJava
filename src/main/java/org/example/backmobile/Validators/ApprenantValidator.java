//package org.example.backmobile.Validators;
//
//import org.SchoolApp.Datas.Entity.ApprenantEntity;
//import org.SchoolApp.Datas.Entity.UserEntity;
//import org.SchoolApp.Datas.Repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ApprenantValidator {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public void validateApprenant(ApprenantEntity apprenant) {
////        validateUser(apprenant.getUser());
////        validateReferentiel(apprenant);
//        // Vous pouvez ajouteœr d'autres validations spécifiques à ApprenantEntity ici
//    }
//
//    private void validateUser(UserEntity user) {
//        if (user == null) {
//            throw new IllegalArgumentException("Les informations de l'utilisateur sont requises.");
//        }
//
//        if (user.getEmail() == null || user.getEmail().isEmpty()) {
//            throw new IllegalArgumentException("L'email de l'utilisateur est requis.");
//        }
//
//        if (userRepository.findByEmail(user.getEmail()) != null) {
//            throw new RuntimeException("Un utilisateur avec cet email existe déjà.");
//        }
//
//    }
//
//    private void validateReferentiel(ApprenantEntity apprenant) {
//        if (apprenant.getReferentiel() == null || apprenant.getReferentiel().getId() == null) {
//            throw new IllegalArgumentException("Le référentiel est requis.");
//        }
//
//        // Si nécessaire, vous pouvez ajouter des validations supplémentaires pour le référentiel
//    }
//}
