package org.example.backmobile.Web.Dtos.Response;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String mail;
    private String type;
    private String telephone;
    private Float solde;
    private String qr;

}
