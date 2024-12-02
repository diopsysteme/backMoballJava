package org.example.backmobile.Services.Impl;


import lombok.extern.slf4j.Slf4j;
import org.example.backmobile.Datas.Entity.User;
import org.example.backmobile.Datas.Repository.UserRepository;
import org.example.backmobile.Services.Interfaces.EmailService;
import org.example.backmobile.Web.Dtos.Mapper.UserMapper;
import org.example.backmobile.Web.Dtos.Request.UserRequestDto;
import org.example.backmobile.Web.Dtos.Response.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prog.dependancy.Services.AbstractService;
import prog.dependancy.Services.QRCodeService;

import java.util.List;

@Slf4j
@Service
public class UserService extends AbstractService<User, UserRequestDto, UserResponseDto> {
    private final UserRepository userRepository;
@Autowired
    EmailService emailService;
@Autowired
QRCodeService qrCodeService;
@Autowired CardHtml cardHtml;

    public UserService(UserRepository repository, UserMapper mapper, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }
@Override
public UserResponseDto save(UserRequestDto dto){
       User ddd= this.mapper.toEntity(dto);
        System.out.println(ddd.toString());
      String  qr= qrCodeService.generateQRCode(ddd.getTelephone());
//      ddd.setQr("dddddddd");
    System.out.println("ici 11");
        User user = repository.save(ddd);
    System.out.println("ici 12");
    String html = cardHtml.generateHtmlCard(qr,ddd.getUsername(),ddd.getTelephone(),"30days");
    System.out.println("ici 2");
    emailService.sendEmail(ddd.getMail(),
            "Votre Carte est disponible",
            "Voici votre carte en piece jointe\n Merci pour votre fidelite",
            html);
    System.out.println("ici 3");
        return mapper.toDto(user);
}

}
