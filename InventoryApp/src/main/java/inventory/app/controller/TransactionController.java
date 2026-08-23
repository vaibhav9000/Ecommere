package inventory.app.controller;

import inventory.app.model.Transaction;
import inventory.app.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/transactions")
@AllArgsConstructor
@PreAuthorize("hasRole('SERVICE_USER')")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable UUID id) {
        return transactionService.getTransaction(id);
    }
}
