package org.example.backmobile.Web.Dtos.Request;

import lombok.Data;

@Data
public class ContactRequestDto {
    private String nom;

    private String prenom;

    private String telephone;
}
