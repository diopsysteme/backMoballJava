package org.example.backmobile.Web.Dtos.Mapper;


import org.example.backmobile.Datas.Entity.User;
//import org.example.backmobile.Datas.Entity.UserEntity;
import org.example.backmobile.Web.Dtos.Request.UserRequestDto;
import org.example.backmobile.Web.Dtos.Response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import prog.dependancy.Web.Mappper.GenericMapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserRequestDto,UserResponseDto> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

}