package org.example.backmobile.Web.Dtos.Response;

import lombok.Data;


import lombok.Data;
import org.example.backmobile.Datas.Enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class TransactionResponseDto {
    private List<SuccessTransactionDTO> success;
    private List<ContactResponseDTO> failed;

  @Data
    public static class SuccessTransactionDTO {
      private Long id;
      private Float montant;
      private String status;
      private LocalDateTime date;
      private Float visibleSolde;
      private TransactionType type;
      private String autrePartie;
    }

  @Data
    public static class ContactResponseDTO {
        private String contact;
        private boolean success;
        private String reason;
    }
}
