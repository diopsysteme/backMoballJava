//package org.example.backmobile.Web.Controller.Impl;
//
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import org.SchoolApp.Services.Interfaces.ApprenantService;
//import org.SchoolApp.Web.Dtos.Mapper.ApprenantMapper;
//import org.SchoolApp.Web.Dtos.Request.ApprenantRequestDto;
//import org.SchoolApp.Web.Dtos.Response.ApprenantResponseDto;
//import org.SchoolApp.Datas.Entity.ApprenantEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
////@RestController
////@RequestMapping("/apprenants")
////public class ApprenantController {
////
////    @Autowired
////    private ApprenantService apprenantService;
////
////    @Autowired
////    private ApprenantMapper apprenantMapper;
////
////    @PostMapping
////    public ResponseEntity<ApprenantResponseDto> createApprenant(@RequestBody ApprenantRequestDto apprenantRequestDto) {
////        // Convert request DTO to entity
////        ApprenantEntity apprenantEntity = apprenantMapper.toEntity(apprenantRequestDto);
////
////        // Call the service layer to handle business logic
////        ApprenantEntity savedApprenant = apprenantService.createApprenant(apprenantEntity, apprenantRequestDto.getUserId(),apprenantRequestDto.getReferentielId());
////
////        // Convert the saved entity to response DTO
////        ApprenantResponseDto responseDto = apprenantMapper.toDto(savedApprenant);
////
////        return ResponseEntity.ok(responseDto);
////    }
////}
