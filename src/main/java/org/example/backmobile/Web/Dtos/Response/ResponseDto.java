package org.example.backmobile.Web.Dtos.Response;

import lombok.Data;

@Data
public class ResponseDto {
    private UserResponseDto user;
    private String token;
}
