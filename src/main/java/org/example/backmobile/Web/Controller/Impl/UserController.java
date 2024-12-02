package org.example.backmobile.Web.Controller.Impl;

import org.example.backmobile.Datas.Entity.User;
import org.example.backmobile.Web.Dtos.Request.UserRequestDto;
import org.example.backmobile.Web.Dtos.Response.UserResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prog.dependancy.Services.AbstractService;
import prog.dependancy.Web.Controller.AbstractController;
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<User, UserRequestDto, UserResponseDto> {
    public UserController(AbstractService<User, UserRequestDto, UserResponseDto> service) {
        super(service);
    }
}
