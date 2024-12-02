package org.example.backmobile.Web.Controller.Impl;


import org.example.backmobile.Datas.Entity.Contact;
import org.example.backmobile.Datas.Entity.Contact;
import org.example.backmobile.Web.Dtos.Request.ContactRequestDto;
import org.example.backmobile.Web.Dtos.Request.ContactRequestDto;
import org.example.backmobile.Web.Dtos.Response.ContactResponseDto;
import org.example.backmobile.Web.Dtos.Response.ContactResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prog.dependancy.Services.AbstractService;
import prog.dependancy.Web.Controller.AbstractController;
@RestController
@RequestMapping("/contact")
public class ContactController extends AbstractController<Contact, ContactRequestDto, ContactResponseDto> {

    public ContactController(AbstractService<Contact, ContactRequestDto, ContactResponseDto> service) {
        super(service);
    }
    
}
