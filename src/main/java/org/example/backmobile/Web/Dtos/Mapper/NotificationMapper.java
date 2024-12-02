package org.example.backmobile.Web.Dtos.Mapper;

import org.example.backmobile.Datas.Entity.Notification;
import org.example.backmobile.Web.Dtos.Request.NotificationRequestDto;
import org.example.backmobile.Web.Dtos.Response.NotificationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import prog.dependancy.Web.Mappper.GenericMapper;
@Component
@Mapper(componentModel = "spring")
public interface NotificationMapper extends GenericMapper<Notification, NotificationRequestDto, NotificationResponseDto> {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);
}
