package org.example.backmobile.Web.Controller.Impl;

import org.example.backmobile.Datas.Entity.ScheduledTransfer;
import org.example.backmobile.Datas.Entity.User;
import org.example.backmobile.Services.Impl.ScheduledTransferService;

import org.example.backmobile.Web.Dtos.Request.ScheduledTransferRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.example.backmobile.Datas.Repository.UserRepository;

import java.security.Principal;

@RestController
@RequestMapping("/api/scheduled-transfers")
public class ScheduledTransferController {

    @Autowired
    private ScheduledTransferService scheduledTransferService;

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/schedule")
    public ScheduledTransfer scheduleTransfer(@RequestBody ScheduledTransferRequestDTO request, Authentication authentication) {
        // Récupérer l'utilisateur connecté depuis `authentication`
        UserDetails sender = (UserDetails) authentication.getPrincipal();

        // Supposez que `userDetails` contient le numéro de téléphone dans un champ `telephone`

        return scheduledTransferService.createScheduledTransfer(request, (User) sender);
    }
    @DeleteMapping("/{id}")
    public void cancelScheduledTransfer(@PathVariable Long id) {
        scheduledTransferService.cancelScheduledTransfer(id);
    }
    @GetMapping("/my-transfers")
    public Page<ScheduledTransfer> getMyScheduledTransfers(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Supposez que `userDetails` contient le numéro de téléphone dans un champ `telephone`
        String senderTelephone = userDetails.getUsername(); // ou une autre méthode pour récupérer le téléphone

        User sender = userRepository.findByTelephone(senderTelephone)
                .orElseThrow(() -> new RuntimeException("User not found with telephone: " + senderTelephone));

        Pageable pageable = PageRequest.of(page, size);
        return scheduledTransferService.getScheduledTransfersForUser(sender, pageable);
    }
}