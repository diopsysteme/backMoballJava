package org.example.backmobile.Web.Dtos.Request;
import lombok.Data;
@Data
public class UserRequestDto {

    private String nom;
    private String prenom;
    private String mail;
    private String telephone;
    private String type;
    private String codeSecret;
    public String getIdentifiant() {
        return telephone + prenom;
    }
    public Float getPlafond() {
        return (float)200000;
    }
    // Method to generate the identifiant by concatenating telephone and prenom
    public Float getSolde() {
        return (float) 0;
    }
}