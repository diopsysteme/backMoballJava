package org.example.backmobile.Services.Impl;

import org.example.backmobile.Datas.Entity.ScheduledTransfer;
import org.example.backmobile.Datas.Entity.User;
import org.example.backmobile.Datas.Repository.ScheduledTransferRepository;
import org.example.backmobile.Datas.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
public class ScheduledTransferExecutor {

    @Autowired
    private ScheduledTransferRepository scheduledTransferRepository;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    UserRepository userRepository;

    @Scheduled(fixedRate = 86400000)
    public void executeScheduledTransfers() {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledTransfer> transfersToExecute = scheduledTransferRepository.findByNextExecutionBeforeAndIsActive(now, true);

        for (ScheduledTransfer scheduledTransfer : transfersToExecute) {
            for (String contactPhone : scheduledTransfer.getContacts()) {
                // Appel de la méthode de transaction pour chaque contact
                User receiver = userRepository.findByTelephone(contactPhone).orElse(null);
                if (receiver != null) {
                    transactionService.processTransaction(scheduledTransfer.getSender(), receiver, scheduledTransfer.getType(), scheduledTransfer.getMontant());
                } else {
                    // Enregistre une erreur pour ce contact
                    log.error("Contact introuvable pour le transfert programmé: " + contactPhone);
                }
            }
            // Mise à jour de la prochaine date d'exécution
            scheduledTransfer.setNextExecution(calculateNextExecution(scheduledTransfer));
            scheduledTransferRepository.save(scheduledTransfer);
        }
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

}
