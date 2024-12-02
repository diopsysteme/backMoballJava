package org.example.backmobile.Web.Controller.Impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.backmobile.Datas.Entity.Transaction;
import org.example.backmobile.Datas.Entity.User;
import org.example.backmobile.Services.Impl.TransactionService;
import org.example.backmobile.Web.Dtos.Request.TransactionRequestDto;
import org.example.backmobile.Web.Dtos.Request.WithdrawalConfirmDto;
import org.example.backmobile.Web.Dtos.Request.WithdrawalRequestDto;
import org.example.backmobile.Web.Dtos.Response.TransactionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prog.dependancy.Services.AbstractService;
import prog.dependancy.Web.Controller.AbstractController;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("/transaction")
public class TransactionController extends AbstractController<Transaction, TransactionRequestDto, TransactionResponseDto> {
    @Autowired
    TransactionService transactionService;
    public TransactionController(AbstractService<Transaction, TransactionRequestDto, TransactionResponseDto> service) {
        super(service);

    }


    @PostMapping("/delete")
    public ResponseEntity<String> deleteTransaction(
            @RequestBody Long transactionId,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;

        try {
            boolean success = transactionService.deleteTransaction(transactionId, user);
            return success ? ResponseEntity.ok("Transaction annulée avec succès")
                    : ResponseEntity.badRequest().body("Échec de l'annulation de la transaction");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Page<TransactionResponseDto.SuccessTransactionDTO>> getUserTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;

        Page<TransactionResponseDto.SuccessTransactionDTO> transactions =
                transactionService.getTransactionsForUser(user, page, size);

        return ResponseEntity.ok(transactions);
    }


    @PostMapping("/withdrawals/initiate")
    public ResponseEntity<TransactionResponseDto.ContactResponseDTO> initiateWithdrawal(
            @RequestBody(required = true, description = "Withdrawal request details")
            
            @Valid @org.springframework.web.bind.annotation.RequestBody WithdrawalRequestDto request,
            Authentication authentication) {
        User user = (User) ((UserDetails) authentication.getPrincipal());

        return Optional.ofNullable(transactionService.initiateWithdrawal(
                        user,
                        request.getTelephone(),
                        request.getMontant()))
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Confirm withdrawal with OTP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdrawal confirmed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid confirmation code")
    })
    @PostMapping("/withdrawals/confirm")
    public ResponseEntity<TransactionResponseDto.SuccessTransactionDTO> confirmWithdrawal(
            @RequestBody(required = true, description = "Withdrawal confirmation details")
            @Valid @org.springframework.web.bind.annotation.RequestBody WithdrawalConfirmDto request,
            Authentication authentication) {
        User user = (User) ((UserDetails) authentication.getPrincipal());

        return Optional.ofNullable(transactionService.confirmWithdrawal(user, request.getCode()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
