package org.example.backmobile.Services.Interfaces;

//import org.example.backmobile.Datas.Entity.UserEntity;
import org.example.backmobile.Datas.Entity.User;
import org.example.backmobile.Web.Dtos.Request.LoginUserDto;
import org.example.backmobile.Web.Dtos.Request.UserRequestDto;

public interface AuthenticationIService {
    public void signup(UserRequestDto input);


    public User authenticate(LoginUserDto input);

}