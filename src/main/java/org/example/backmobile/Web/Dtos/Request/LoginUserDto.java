package org.example.backmobile.Web.Dtos.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserDto {
    private String telephone;
    private String password;
}
