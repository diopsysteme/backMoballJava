package org.example.backmobile.Web.Dtos.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.example.backmobile.Datas.Enums.TransactionType;

import java.util.List;

@Data
public class TransactionRequestDto {
    private List<String> contacts;
    private Float montant;
    private TransactionType type;

}