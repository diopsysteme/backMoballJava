package org.example.backmobile.Services.Impl;

import org.example.backmobile.Datas.Entity.ScheduledTransfer;
import org.example.backmobile.Datas.Entity.User;
import org.example.backmobile.Datas.Repository.ScheduledTransferRepository;
import org.example.backmobile.Web.Dtos.Request.ScheduledTransferRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prog.dependancy.Services.AbstractService;

import java.time.LocalDateTime;
@Service
public class ScheduledTransferService  {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ScheduledTransferRepository scheduledTransferRepository;

    public ScheduledTransfer createScheduledTransfer(ScheduledTransferRequestDTO request, User sender) {
        System.out.println("createScheduledTransfer called"+request.toString());
        ScheduledTransfer scheduledTransfer = new ScheduledTransfer();
        scheduledTransfer.setSender(sender);
        scheduledTransfer.setContacts(request.getContacts());
        scheduledTransfer.setMontant(request.getMontant());
        scheduledTransfer.setType(request.getType());
        scheduledTransfer.setFrequency(request.getFrequency());
        scheduledTransfer.setIntervalDays(request.getIntervalDays());

        // Déterminer la prochaine date d'exécution
        scheduledTransfer.setNextExecution(calculateNextExecution(scheduledTransfer));

        return scheduledTransferRepository.save(scheduledTransfer);
    }

    public void cancelScheduledTransfer(Long transferId) {
        ScheduledTransfer scheduledTransfer = scheduledTransferRepository.findById(transferId)
                .orElseThrow(() -> new RuntimeException("Scheduled transfer not found"));
        scheduledTransfer.setActive(false);
        scheduledTransferRepository.save(scheduledTransfer);
    }

    private LocalDateTime calculateNextExecution(ScheduledTransfer scheduledTransfer) {
        LocalDateTime now = LocalDateTime.now();
        switch (scheduledTransfer.getFrequency()) {
            case "DAILY":
                return now.plusDays(1);
            case "WEEKLY":
                return now.plusWeeks(1);
            case "MONTHLY":
                return now.plusMonths(1);
            case "EVERY_X_DAYS":
                return now.plusDays(scheduledTransfer.getIntervalDays());
            default:
                throw new IllegalArgumentException("Unknown frequency type");
        }
    }
    public Page<ScheduledTransfer> getScheduledTransfersForUser(User user, Pageable pageable) {
        return scheduledTransferRepository.findBySenderAndIsActive(user, true, pageable);
    }
}
