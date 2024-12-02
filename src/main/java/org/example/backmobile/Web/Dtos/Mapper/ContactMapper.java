package org.example.backmobile.Web.Dtos.Mapper;

import org.example.backmobile.Datas.Entity.Contact;
import org.example.backmobile.Web.Dtos.Request.ContactRequestDto;
import org.example.backmobile.Web.Dtos.Response.ContactResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import prog.dependancy.Web.Mappper.GenericMapper;
@Component
@Mapper(componentModel = "spring")
public interface ContactMapper extends GenericMapper<Contact, ContactRequestDto, ContactResponseDto> {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);
}
