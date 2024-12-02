package org.example.backmobile.Web.Dtos.Response;

import lombok.Data;
import org.example.backmobile.Web.Dtos.BaseDto;

@Data
public class ContactResponseDto extends BaseDto {

    private String nom;

    private String prenom;

    private String telephone;
}
