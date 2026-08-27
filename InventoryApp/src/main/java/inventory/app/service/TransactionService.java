package inventory.app.service;

import inventory.app.enums.TransactionRequest;
import inventory.app.exception.ResourceNotFoundException;
import inventory.app.model.Product;
import inventory.app.model.Transaction;
import inventory.app.model.Warehouse;
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

    public Transaction addTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setInventoryId(request.getInventoryId());
        transaction.setOrderId(request.getOrderId());
        transaction.setUserId(request.getUserId());
        transaction.setType(request.getType());
        transaction.setQuantityChange(request.getQuantityChange());
        transaction.setNotes(request.getNotes());
        return transactionRepository.save(transaction);
    }
}
