//package org.example.backmobile.Services.Impl;
//
//import org.SchoolApp.Datas.Entity.ApprenantEntity;
//import org.SchoolApp.Datas.Entity.PromoEntity;
//import org.SchoolApp.Datas.Entity.ReferentielEntity;
//import org.SchoolApp.Datas.Entity.UserEntity;
//import org.SchoolApp.Datas.Enums.StatusEnum;
//import org.SchoolApp.Datas.Repository.ApprenantRepository;
//import org.SchoolApp.Datas.Repository.PromoRepository;
//import org.SchoolApp.Datas.Repository.ReferentielRepository;
//import org.SchoolApp.Datas.Repository.UserRepository;
//import org.SchoolApp.Services.Interfaces.ApprenantService;
//import org.SchoolApp.Services.Interfaces.EmailService;
//import org.SchoolApp.Services.Interfaces.ExcelIService;
//import org.SchoolApp.Services.Interfaces.QRCodeService;
//import org.SchoolApp.Web.Dtos.Mapper.ApprenantMapper;
//import org.SchoolApp.Web.Dtos.Request.ApprenantRequestDto;
//import org.SchoolApp.Web.Dtos.Response.ApprenantResponseDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import prog.dependancy.Services.AbstractService;
//import prog.dependancy.Web.Mappper.GenericMapper;
//
//import java.io.IOException;
//import java.util.*;
//
//@Service
//@Primary
//public class ApprenantService2 extends AbstractService<ApprenantEntity, ApprenantRequestDto, ApprenantResponseDto> implements ApprenantService   {
//    @Autowired
//    private UserRepository userRepository;
//@Autowired
//private PromoRepository promoRepository;
//    @Autowired
//    private ReferentielRepository referentielRepository;
//
//    @Autowired
//    private QRCodeService qrCodeService;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private ExcelIService excelService;
//
//    @Autowired
//    public ApprenantService2(ApprenantRepository repository, ApprenantMapper mapper) {
//        this.repository = repository;
//        this.mapper = mapper;
//    }
//    @Value("${myapp.secret.student}")
//    private String studentSecret;
//    @Override
//    public Optional<ApprenantResponseDto> findById(Long id) {
//        return super.findById(id);  // This uses the implementation from AbstractService
//    }
//
//    @Override
//    public List<ApprenantResponseDto> findAll() {
//        return super.findAll();  // This uses the implementation from AbstractService
//    }
//
//    @Override
//    public ApprenantResponseDto save(ApprenantRequestDto apprenantDto) {
//        ApprenantEntity apprenant =mapper.toEntity(apprenantDto);
//        UserEntity user = userRepository.findById(apprenantDto.getUserId()).orElseThrow();
//
//        // Générer un mot de passe par défaut et le hacher
//        String defaultPassword = studentSecret;
//        user.setPassword(hashPassword(defaultPassword));
//
//        // Définir le statut par défaut si non défini
//        if (user.getStatus() == null) {
//            user.setStatus(StatusEnum.ACTIF);
//        }
//
//        // Sauvegarder l'utilisateur
//        userRepository.save(user);
//
//        apprenant.setUser(user);
//
//        ReferentielEntity referentiel = referentielRepository.findById(apprenantDto.getReferentielId())
//                .orElseThrow(() -> new RuntimeException("Référentiel non trouvé."));
//        apprenant.setReferentiel(referentiel);
//
//        String matricule = generateMatricule();
//        apprenant.setMatricule(matricule);
//
//        String qrCodeLink = qrCodeService.generateQRCode(matricule);
//        apprenant.setQrCodeLink(qrCodeLink);
//
//        ApprenantEntity savedApprenant = repository.save(apprenant);
//
//        emailService.sendAuthenticationEmail(user.getEmail(), user.getEmail(), defaultPassword);
//
//        return mapper.toDto(savedApprenant);  // This uses the implementation from AbstractService
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        super.deleteById(id);  // This uses the implementation from AbstractService
//    }
//
//    @Override
//    public ApprenantResponseDto update(Long id, ApprenantRequestDto apprenantDto) {
//        apprenantDto.setId(id);  // Ensure the ID is set for the update
//        return super.update(id, apprenantDto);  // This uses the implementation from AbstractService
//    }
//
//    @Override
//    public ApprenantEntity createApprenant(ApprenantEntity apprenant, Long userId, Long referentielId) {
//        return null;
//    }
//    private String generateMatricule() {
//        return "MAT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
//    }
//
//
//
//    private String hashPassword(String password) {
//         return new BCryptPasswordEncoder().encode(password);
//    }
//    @Override
//    public List<ApprenantEntity> importApprenants(MultipartFile file) throws IOException {
//        List<ApprenantEntity> apprenantsCreated = new ArrayList<>();
//
//        // Appeler la méthode pour importer les données du fichier Excel
//        List<Map<String, String>> excelData = excelService.importExcelData(file);
//
//        // Parcourir chaque ligne (excluant la première ligne qui est l'en-tête)
//        for (Map<String, String> row : excelData) {
//            // Récupérer les données pour l'utilisateur à partir des clés dans le Map
//            String email = row.get("email");
//            String password = studentSecret; // Mot de passe par défaut
//            String nom = row.get("nom");
//            String prenom = row.get("prenom");
//            String adresse = row.get("adresse");
//            String telephone = row.get("telephone");
//            String photo = row.get("photo");
//
//            // Créer l'utilisateur
//            UserEntity user = new UserEntity();
//            user.setEmail(email);
//            user.setPassword(hashPassword(studentSecret)); // Hachage du mot de passe
//            user.setNom(nom);
//            user.setPrenom(prenom);
//            user.setAdresse(adresse);
//            user.setTelephone(telephone);
//            user.setPhoto(photo);
//            user.setStatus(StatusEnum.ACTIF);  // Statut par défaut
//
//            // Sauvegarder l'utilisateur
//            userRepository.save(user);
//
//            // Récupérer les données pour l'apprenant
//            String nomTuteur = row.get("nomTuteur");
//            String prenomTuteur = row.get("prenomTuteur");
//            String contactTuteur = row.get("contactTuteur");
//            String cniFile = row.get("cniFile");
//            String extraitFile = row.get("extraitFile");
//            String diplomeFile = row.get("diplomeFile");
//            String casierFile = row.get("casierFile");
//            String photoCouverture = row.get("photoCouverture");
//
//            // Récupérer les IDs des référentiel et promotion
//            Long referentielId = Long.parseLong(row.get("referentielId"));
//            Long promoId = Long.parseLong(row.get("promoId"));
//
//            // Créer l'apprenant
//            ApprenantEntity apprenant = new ApprenantEntity();
//            apprenant.setNomTuteur(nomTuteur);
//            apprenant.setPrenomTuteur(prenomTuteur);
//            apprenant.setContactTuteur(contactTuteur);
//            apprenant.setCniFile(cniFile);
//            apprenant.setExtraitFile(extraitFile);
//            apprenant.setDiplomeFile(diplomeFile);
//            apprenant.setCasierFile(casierFile);
//            apprenant.setPhotoCouverture(photoCouverture);
//            apprenant.setUser(user); // Associer l'utilisateur
//
//            // Récupérer le référentiel
//            ReferentielEntity referentiel = referentielRepository.findById(referentielId)
//                    .orElseThrow(() -> new RuntimeException("Référentiel non trouvé pour l'ID " + referentielId));
//            apprenant.setReferentiel(referentiel);
//
//            // Récupérer la promotion
//            PromoEntity promo = promoRepository.findById(promoId)
//                    .orElseThrow(() -> new RuntimeException("Promotion non trouvée pour l'ID " + promoId));
//            apprenant.setPromo(promo);
//
//            // Générer un matricule unique et un QR code
//            String matricule = generateMatricule();
//            apprenant.setMatricule(matricule);
//            String qrCodeLink = qrCodeService.generateQRCode(matricule);
//            apprenant.setQrCodeLink(qrCodeLink);
//
//            // Sauvegarder l'apprenant
//            repository.save(apprenant);
//            apprenantsCreated.add(apprenant);
//
//            // Envoyer un e-mail de bienvenue avec les informations de connexion
//            emailService.sendAuthenticationEmail(user.getEmail(), user.getEmail(), studentSecret);
//        }
//
//        return apprenantsCreated;
//    }
//
//
//
//}
