package org.example.backmobile.Web.Dtos.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WithdrawalConfirmDto {
    @NotBlank(message = "Le code est requis")
    private String code;

}
