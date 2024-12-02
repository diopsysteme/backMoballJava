//package org.example.backmobile.Web.Dtos.Request;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Positive;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.SchoolApp.Datas.Entity.CompetencesEntity;
//import org.SchoolApp.Validators.UniqueField;
//
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class CompetenceDTO {
//    @UniqueField(entity = CompetencesEntity.class, field = "nom")
//    @NotBlank
//    private String nom;
//    @NotBlank
//    private String description;
//    @Positive
//    @NotNull
//    private int duree_acquisition;
//    @NotBlank
//    private String type;
//    private List<ModuleDTO> modules;
//}
