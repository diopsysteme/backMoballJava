package org.example.backmobile.Web.Dtos.Request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data

public class WithdrawalRequestDto {
    @NotBlank(message = "Telephone is required")
    private String telephone;

    @NotNull(message = "Montant is required")
    @Positive(message = "Montant must be greater than zero")
    private Float montant;
}

