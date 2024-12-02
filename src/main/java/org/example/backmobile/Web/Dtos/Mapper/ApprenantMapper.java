//package org.example.backmobile.Web.Dtos.Mapper;
//
//import org.SchoolApp.Datas.Entity.ApprenantEntity;
//import org.SchoolApp.Web.Dtos.BaseDto;
//import org.SchoolApp.Web.Dtos.Request.ApprenantRequestDto;
//import org.SchoolApp.Web.Dtos.Response.ApprenantDto;
//import org.SchoolApp.Web.Dtos.Response.ApprenantResponseDto;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//import org.mapstruct.factory.Mappers;
//import org.springframework.stereotype.Component;
//import prog.dependancy.Web.Mappper.GenericMapper;
//@Component
//@Mapper(componentModel = "spring")
//public interface ApprenantMapper extends GenericMapper<ApprenantEntity, ApprenantRequestDto, ApprenantResponseDto> {
//    ApprenantMapper INSTANCE = Mappers.getMapper(ApprenantMapper.class);
//}
//
//
//
////package org.SchoolApp.Web.Dtos.Mapper;
////
////import org.SchoolApp.Datas.Entity.ApprenantEntity;
////import org.SchoolApp.Web.Dtos.Request.ApprenantRequestDto;
////import org.SchoolApp.Web.Dtos.Response.ApprenantResponseDto;
////import org.mapstruct.Mapper;
////import org.mapstruct.Mapping;
////import org.mapstruct.Mappings;
////
////@Mapper(componentModel = "spring", uses = {UserMapper.class, ReferentielMapper.class, PromoMapper.class})
////public interface ApprenantMapper {
////
////
////    ApprenantEntity toEntity(ApprenantRequestDto dto);
////
////    ApprenantResponseDto toDto(ApprenantEntity entity);
////}