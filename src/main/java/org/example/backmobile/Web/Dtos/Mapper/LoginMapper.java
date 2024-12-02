package org.example.backmobile.Web.Dtos.Mapper;


import org.example.backmobile.Services.Interfaces.JwtServiceInterface;
import org.example.backmobile.Web.Dtos.Response.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    LoginResponse toDto(JwtServiceInterface jwtServiceInterface);
}
