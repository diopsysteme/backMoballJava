    package org.example.backmobile.Services.Impl;

    import jakarta.transaction.Transactional;
    import org.example.backmobile.Datas.Entity.Transaction;
    import org.example.backmobile.Datas.Entity.User;
    import org.example.backmobile.Datas.Enums.TransactionType;
    import org.example.backmobile.Datas.Repository.TransactionRepository;
    import org.example.backmobile.Datas.Repository.UserRepository;
    import org.example.backmobile.Services.Interfaces.EmailService;
    import org.example.backmobile.Services.Interfaces.ITransactionService;
    import org.example.backmobile.Web.Dtos.Mapper.TransactionMapper;
    import org.example.backmobile.Web.Dtos.Request.TransactionRequestDto;
    import org.example.backmobile.Web.Dtos.Response.TransactionResponseDto;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Component;
    import org.springframework.stereotype.Service;
    import prog.dependancy.Services.AbstractService;

    import java.security.SecureRandom;
    import java.time.Duration;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;
    import java.util.concurrent.TimeUnit;

    @Service
    public class TransactionService extends AbstractService<Transaction, TransactionRequestDto, TransactionResponseDto> implements ITransactionService {
        private UserRepository userRepository;

        private static final float FRAIS_PERCENTAGE = 0.01f; // 1% de frais, à ajuster selon vos besoins
        @Autowired
        private RedisTemplate<String, String> redisTemplate;

        @Autowired
        private EmailService emailService; // You'll need to create this service

        private static final int OTP_LENGTH = 6;

        @Autowired
        private TransactionRepository trrepository;
        public TransactionService(
                TransactionRepository repository,
                TransactionMapper mapper,
                UserService userService,UserRepository userRepository) {
            this.repository = repository;
            this.mapper = mapper;
            this.userRepository = userRepository;
        }
        @Transactional
        public Object processTransaction(User sender, User receiver, TransactionType type, Float montant) {
            if (sender == null) {
                throw new IllegalArgumentException("Sender cannot be null");
            }
            if ((type == TransactionType.RETRAIT || type == TransactionType.TRANSFERT) && sender.getSolde() < montant) {
                return false;
            }

            float frais = 0f;

            // Effectuer la transaction selon le type
            switch (type) {
                case DEPOT:
                    // Dépôt : on ajoute le montant au solde du receiver
                    if (receiver != null) {
                        receiver.setSolde(receiver.getSolde() + montant);
                        userRepository.save(receiver);
                    } else {
                        return false;
                    }
                    break;

                case RETRAIT:
                    // Retrait : on déduit le montant du solde du sender
                    sender.setSolde(sender.getSolde() - montant);
                    userRepository.save(sender);
                    break;

                case TRANSFERT:
                    // Transfert : on déduit le montant du sender et on l'ajoute au receiver
                    if (receiver != null) {
                        sender.setSolde(sender.getSolde() - montant);
                        receiver.setSolde(receiver.getSolde() + montant);
                        userRepository.save(sender);
                        userRepository.save(receiver);
                    } else {
                        return false;
                    }
                    break;

                default:
                    return false;
            }

            // Créer et enregistrer la transaction
            Transaction transaction = new Transaction();
            transaction.setMontant(montant);
            transaction.setStatus("COMPLETED");
            transaction.setDate(LocalDateTime.now());
            transaction.setSoldeSender(sender.getSolde());
            transaction.setSoldeReceiver(receiver != null ? receiver.getSolde() : null);
            transaction.setFrais(frais);
            transaction.setType(type);
            transaction.setSender(sender);
            transaction.setReceiver(receiver);
            Transaction tr =repository.save(transaction);
            System.out.println(tr.toString());
            return tr;
        }
        @Override
        public TransactionResponseDto save(TransactionRequestDto request) {
            System.out.println(request.toString());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Vérifier que l'authentification n'est pas nulle
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new SecurityException("Aucun utilisateur connecté");
            }

            // Récupérer les détails de l'utilisateur
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Si vous avez une classe User qui étend UserDetails
            User sender = (User) userDetails;
            System.out.println("ici avant tousnbjsbjbs ###########################################");
            List<TransactionResponseDto.SuccessTransactionDTO> transactionsEffectuees = new ArrayList<>();
            List<TransactionResponseDto.ContactResponseDTO> echec = new ArrayList<>();
            System.out.println("ici avant tous");
            System.out.println(request.toString());
            for (String contactPhone : request.getContacts()) {
                // Recherche le contact dans la table User
                System.out.println("ici dans la boucle");
                User receiver = userRepository.findByTelephone(contactPhone).orElse(null);

                // Si le contact n'existe pas, ajouter à la liste d'échec
                if (receiver == null) {
                    System.out.println("null receiver");
                    TransactionResponseDto.ContactResponseDTO contactResponse = new TransactionResponseDto.ContactResponseDTO();
                    contactResponse.setContact(contactPhone);
                    contactResponse.setSuccess(false);
                    contactResponse.setReason("Contact introuvable");
                    echec.add(contactResponse);
                    continue;
                }
                System.out.println("ici creation bef");
                // Appeler la fonction de transaction pour effectuer la transaction
                Object transactionResult = processTransaction(sender, receiver, request.getType(), request.getMontant());
                System.out.println("ici after creation");
                
                if (transactionResult instanceof Transaction) {
                    // Convertir la transaction en DTO
                    System.out.println("ici bonne transaction");
                    TransactionResponseDto.SuccessTransactionDTO successTransaction = formatTransaction((Transaction) transactionResult,sender);

                    transactionsEffectuees.add(successTransaction);
                } else {
                    TransactionResponseDto.ContactResponseDTO contactResponse = new TransactionResponseDto.ContactResponseDTO();
                    contactResponse.setContact(contactPhone);
                    contactResponse.setSuccess(false);
                    contactResponse.setReason("Erreur lors du transfert");
                    echec.add(contactResponse);
                }
            }

            // Créer et retourner la réponse finale
            TransactionResponseDto response = new TransactionResponseDto();
            response.setSuccess(transactionsEffectuees);
            response.setFailed(echec);
            return response;
        }



        public boolean deleteTransaction(Long transactionId, User user) {
            Optional<Transaction> transactionOpt = repository.findById(transactionId);

            if (transactionOpt.isEmpty()) {
                throw new RuntimeException("Transaction introuvable");
            }

            Transaction transaction = transactionOpt.get();

            // Vérifier si l'utilisateur connecté est le sender de la transaction
            if (!transaction.getSender().equals(user)) {
                throw new RuntimeException("Vous ne pouvez annuler que vos propres transactions");
            }

            // Vérifier si la transaction a été effectuée dans les 30 dernières minutes
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(transaction.getDate(), now);

            if (duration.toMinutes() > 30) {
                throw new RuntimeException("Le délai d'annulation de la transaction est dépassé (30 minutes)");
            }

            // Effectuer l'annulation : inverser les montants des comptes
            transaction.getSender().setSolde(transaction.getSender().getSolde() + transaction.getMontant());
            if (transaction.getReceiver() != null) {
                transaction.getReceiver().setSolde(transaction.getReceiver().getSolde() - transaction.getMontant());
                userRepository.save(transaction.getReceiver());
            }
            userRepository.save(transaction.getSender());

            // Mettre à jour le statut de la transaction comme annulée
            transaction.setStatus("CANCELED");
            repository.save(transaction);

            return true;
        }

        public Page<TransactionResponseDto.SuccessTransactionDTO> getTransactionsForUser(User user, int page, int size) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Transaction> transactionsPage = trrepository.findAllBySenderOrReceiver(user, pageable);

            return transactionsPage.map(transaction -> {
                return formatTransaction(transaction,user);
            });
        }
        // Fonction pour formater une liste de transactions
        @Transactional
        public List<TransactionResponseDto.SuccessTransactionDTO> formatTransactions(List<Transaction> transactions, User connectedUser) {
            List<TransactionResponseDto.SuccessTransactionDTO> formattedTransactions = new ArrayList<>();

            for (Transaction transaction : transactions) {
                TransactionResponseDto.SuccessTransactionDTO dto = formatTransaction(transaction, connectedUser);

                if (dto != null) {  // Assurez-vous d'ajouter seulement les transactions pertinentes
                    formattedTransactions.add(dto);
                }
            }

            return formattedTransactions;
        }



        // Fonction générique pour formater une seule transaction
        private TransactionResponseDto.SuccessTransactionDTO formatTransaction(Transaction transaction, User connectedUser) {
            TransactionResponseDto.SuccessTransactionDTO dto = new TransactionResponseDto.SuccessTransactionDTO();

            // Remplir les propriétés de la DTO
            dto.setId(transaction.getId());
            dto.setMontant(transaction.getMontant());
            dto.setStatus(transaction.getStatus());
            dto.setDate(transaction.getDate());

            // Déterminer le type et les informations visibles
            if (transaction.getSender().equals(connectedUser)) {
                // Si l'utilisateur est le sender
                dto.setType(TransactionType.TRANSFERT);
                dto.setVisibleSolde(transaction.getSoldeSender());
                dto.setAutrePartie(transaction.getReceiver() != null ? transaction.getReceiver().getIdentifiant() : transaction.getReceiverString());
            } else if (transaction.getReceiver().equals(connectedUser)) {
                // Si l'utilisateur est le receiver
                dto.setType(TransactionType.RECU);
                dto.setVisibleSolde(transaction.getSoldeReceiver());
                dto.setAutrePartie(transaction.getSender().getIdentifiant());
            } else {
                // Si l'utilisateur n'est pas impliqué dans la transaction (cas improbable)
                return null; // Ou vous pouvez gérer ce cas différemment
            }

            return dto;
        }

        public TransactionResponseDto.ContactResponseDTO initiateWithdrawal(User agent, String clientPhone, Float amount) {

            // Verify agent status
            if (!"AGENT".equals(agent.getType())) {
                throw new RuntimeException("Only agents can initiate withdrawals");
            }

            // Find client
            User client = userRepository.findByTelephone(clientPhone)
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            // Check client balance
            if (client.getSolde() < amount) {
                throw new RuntimeException("Insufficient funds for withdrawal");
            }

            // Generate OTP
            String otp = generateOtp();

            // Create Redis key and store withdrawal data
            String redisKey = String.format("%s:%s", agent.getTelephone(), otp);
            WithdrawalData withdrawalData = new WithdrawalData(
                    client.getId(),
                    amount,
                    "RETRAIT"
            );

            redisTemplate.opsForValue().set(
                    redisKey,
                    withdrawalData.toString(),
                    5, // Expiration time
                    TimeUnit.MINUTES
            );

            // Send email to client
            String emailContent = String.format("Vous allez retirer %f. Voici votre code: %s", amount, otp);
            emailService.sendEmail(client.getMail(), "Code de Retrait", emailContent);

            // Set response
            List<TransactionResponseDto.ContactResponseDTO> success = new ArrayList<>();
            TransactionResponseDto.ContactResponseDTO contactResponse = new TransactionResponseDto.ContactResponseDTO();
            contactResponse.setContact(clientPhone);
            contactResponse.setSuccess(true);
            contactResponse.setReason("Code de retrait envoyé par email");

            return contactResponse;
        }

        /**
         * Confirm withdrawal with OTP (Second step)
         */
        public TransactionResponseDto.SuccessTransactionDTO confirmWithdrawal(User agent, String otp) {
            String redisKey = String.format("%s:%s", agent.getTelephone(), otp);
            String cachedData = redisTemplate.opsForValue().get(redisKey);
            if (cachedData == null) {
                throw new RuntimeException("Invalid or expired code");
            }

            WithdrawalData withdrawalData = WithdrawalData.fromString(cachedData);
            User client = userRepository.findById(withdrawalData.clientId)
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            if (client.getSolde() < withdrawalData.amount) {
                throw new RuntimeException("Insufficient funds");
            }

            Object transaction = processTransaction(client, agent, TransactionType.RETRAIT, withdrawalData.amount);
            redisTemplate.delete(redisKey);
            TransactionResponseDto.SuccessTransactionDTO tr= formatTransaction((Transaction) transaction,agent);
//            TransactionResponseDto response = new TransactionResponseDto();
//            List<TransactionResponseDto.SuccessTransactionDTO> success = new ArrayList<>();
//            TransactionResponseDto.SuccessTransactionDTO transactionResponse = new TransactionResponseDto.SuccessTransactionDTO();
//
//            transactionResponse.setMontant(withdrawalData.amount);
//            transactionResponse.setStatus("COMPLETED");
//            transactionResponse.setDate(LocalDateTime.now());
//            transactionResponse.setSoldeSender(client.getSolde() - withdrawalData.amount);
//            transactionResponse.setSoldeReceiver(client.getSolde());
//            transactionResponse.setFrais(0.0f);
//            transactionResponse.setType(TransactionType.RETRAIT);
//            transactionResponse.setSenderPhone(agent.getTelephone());
//            transactionResponse.setReceiverPhone(client.getTelephone());
//            transactionResponse.setReceiverString(client.getPrenom() + " " + client.getNom());

//            success.add(transactionResponse);
//            response.setSuccess(success);
            return tr;
        }

        /**
         * Generate a random OTP
         */
        private String generateOtp() {
            SecureRandom random = new SecureRandom();
            StringBuilder otp = new StringBuilder();

            for (int i = 0; i < OTP_LENGTH; i++) {
                otp.append(random.nextInt(10));
            }

            return otp.toString();
        }

        /**
         * Inner class to handle withdrawal data storage
         */
        private static class WithdrawalData {
            private Long clientId;
            private Float amount;
            private String type;

            public WithdrawalData(Long clientId, Float amount, String type) {
                this.clientId = clientId;
                this.amount = amount;
                this.type = type;
            }

            @Override
            public String toString() {
                return String.format("%d:%f:%s", clientId, amount, type);
            }

            public static WithdrawalData fromString(String str) {
                String[] parts = str.split(":");
                return new WithdrawalData(
                        Long.parseLong(parts[0]),
                        Float.parseFloat(parts[1]),
                        parts[2]
                );
            }

        }}