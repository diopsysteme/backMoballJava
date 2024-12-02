//package org.example.backmobile.Web.Controller.Impl;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import org.SchoolApp.Datas.Entity.ApprenantEntity;
//import org.SchoolApp.Services.Interfaces.ApprenantService;
//import org.SchoolApp.Web.Dtos.Request.ApprenantRequestDto;
//import org.SchoolApp.Web.Dtos.Response.ApprenantDto;
//import org.SchoolApp.Web.Dtos.Response.ApprenantResponseDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import prog.dependancy.Services.AbstractService;
//import prog.dependancy.Web.Controller.AbstractController;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/test-avec-apprenant")
//public class TestDependance extends AbstractController<ApprenantEntity, ApprenantRequestDto, ApprenantResponseDto> {
//   @Autowired
//    private ApprenantService serviceZ ;
//    public TestDependance(AbstractService<ApprenantEntity, ApprenantRequestDto ,ApprenantResponseDto> service) {
//        super(service);
//    }
//    @Operation(
//            summary = "Upload a file",
//            description = "Upload a file using multipart/form-data",
//            requestBody = @RequestBody(
//                    content = @Content(
//                            mediaType = "multipart/form-data",
//                            schema = @Schema(
//                                    type = "object",
//                                    format = "binary",
//                                    requiredProperties = {"file"}
//                            )
//                    )
//            ),
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
//                    @ApiResponse(responseCode = "400", description = "Invalid request")
//            }
//    )
//
//    @PostMapping("/import")
//    public ResponseEntity<?> importApprenants(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le fichier est vide.");
//        }
//        try {
//            List<ApprenantEntity> apprenantsCreated = serviceZ.importApprenants(file);
//            return ResponseEntity.ok(apprenantsCreated);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la lecture du fichier Excel.");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//}
