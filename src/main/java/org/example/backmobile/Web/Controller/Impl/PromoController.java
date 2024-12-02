//package org.example.backmobile.Web.Controller.Impl;
//
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import jakarta.validation.Valid;
//import org.SchoolApp.Datas.Entity.PromoEntity;
//import org.SchoolApp.Datas.Entity.ReferentielEntity;
//import org.SchoolApp.Datas.Enums.EtatEnum;
//import org.SchoolApp.Web.Dtos.Request.PromoRequest;
//import org.SchoolApp.Services.Impl.PromoService;
//import org.SchoolApp.Web.Dtos.Request.PromoUpdateRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/promo")
//public class PromoController {
//    @Autowired
//    private PromoService promoService;
//
//    @PostMapping("")
//    public ResponseEntity<PromoEntity> Create(@Valid @RequestBody PromoRequest promoEntity){
//        PromoEntity promo = promoService.createPromo(promoEntity);
//        return ResponseEntity.ok(promo);
//    }
//
//    @GetMapping("")
//    public ResponseEntity<List<PromoEntity>> findAll(){
//       return ResponseEntity.ok(promoService.getAllPromos());
//    }
//
//    @GetMapping("/encours")
//    public PromoEntity getPromoEncours(){
//        return promoService.getActivePromo();
//    }
//
//    @PatchMapping("/{id}/etat")
//    public List<PromoEntity> updateEtatPromo(@PathVariable Long id, @RequestBody EtatEnum etat){
//        return promoService.updateEtat(id, etat);
//    }
//
//    @GetMapping("/{id}")
//    public Optional<PromoEntity> findById(@PathVariable Long id){
//        return promoService.findById(id);
//    }
//
//    @PatchMapping("/{id}")
//    public PromoEntity Update(@PathVariable Long id, @RequestBody PromoUpdateRequest promoUpdateRequest){
//        return promoService.update(id, promoUpdateRequest);
//    }
//
//
//
//    @PatchMapping("/{id}/cloturer")
//    public PromoEntity cloturerPromo(@PathVariable Long id){
//        return promoService.cloturePromo(id);
//    }
//
//    @GetMapping("{id}/refentiels")
//    public Set<ReferentielEntity> getPromoRefentiels(@PathVariable Long id){
//        return promoService.findReferentielActif(id);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PatchMapping("{id}/referentiels")
//    public Void deleteReferentiel(@PathVariable Long id, @RequestBody Long referentielId){
//        promoService.deleteReferentiel(id,referentielId);
//        return null;
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PostMapping("{id}/restore")
//    public Void restorePromo(@PathVariable Long id,@RequestBody Long referentielId){
//        promoService.restoreRefentiel(id,referentielId);
//        return null;
//    }
//
//}
