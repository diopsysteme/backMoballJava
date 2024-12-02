package org.example.backmobile.Services.Impl;

import org.example.backmobile.Datas.Entity.Contact;
import org.example.backmobile.Datas.Repository.ContactRepository;
import org.example.backmobile.Web.Dtos.Mapper.ContactMapper;
import org.example.backmobile.Web.Dtos.Request.ContactRequestDto;
import org.example.backmobile.Web.Dtos.Response.ContactResponseDto;
import org.springframework.stereotype.Service;
import prog.dependancy.Services.AbstractService;
@Service
public class ContactService extends AbstractService<Contact, ContactRequestDto, ContactResponseDto> {
    public ContactService(ContactRepository repository, ContactMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

}
