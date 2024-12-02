//package org.example.backmobile.Web.Dtos.Mapper;
//
//import org.SchoolApp.Datas.Entity.ApprenantEntity;
//import org.SchoolApp.Datas.Entity.ModulesEntity;
//import org.SchoolApp.Datas.Entity.NotesEntity;
//import org.SchoolApp.Datas.Repository.ApprenantRepository;
//import org.SchoolApp.Datas.Repository.ModulesRepository;
//import org.SchoolApp.Web.Dtos.Request.NoteRequest;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Mapper(componentModel = "spring")
//public abstract class NoteRequestMapper {
//
//    @Autowired
//    protected ApprenantRepository apprenantRepository;
//
//    @Autowired
//    protected ModulesRepository modulesRepository;
//
//    public abstract NotesEntity toEntity(NoteRequest noteRequest);
//
//    public List<NotesEntity> toEntities(List<NoteRequest> noteRequests){
//        return noteRequests.stream().map(this::toEntity).collect(Collectors.toList());
//    }
//
//    public ApprenantEntity getApprenantEntity(Long id){
//        return apprenantRepository.findById(id).orElse(null);
//    }
//
//    public ModulesEntity getModulesEntity(Long id){
//        return modulesRepository.findById(id).orElse(null);
//    }
//}
