package inventory.app.service;

import inventory.app.exception.ResourceNotFoundException;
import inventory.app.model.Transaction;
import inventory.app.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransaction(UUID id) {
        return transactionRepository.findById(id).orElseThrow(() -> {
            log.warn("Transaction not found for id: {}", id);
            return new ResourceNotFoundException("Transaction", id);
        });
    }
}
