package org.example.backmobile.Web.Dtos.Request;

import lombok.Data;
import org.example.backmobile.Datas.Enums.TransactionType;

import java.util.List;

@Data
public class ScheduledTransferRequestDTO  {
    private List<String> contacts;
    private Float montant;
    private TransactionType type;
    private String frequency; // Ex. "DAILY", "WEEKLY", "MONTHLY", "EVERY_X_DAYS"
    private int intervalDays; // Pour les fréquences personnalisées
}
