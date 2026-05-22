package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountService {

    private AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    // ─── Create Account ──────────────────────────────────────────────────────
    public Account createAccount(Account account) {
        // Apply default minimum balance if not provided
        if (account.getMinimumBalance() == null || account.getMinimumBalance() < 0) {
            account.setMinimumBalance(500.0);
        }
        if (account.getBalance() == null) {
            account.setBalance(0.0);
        }
        account.setBlocked(false);
        account.setBlockedReason(null);

        // Auto-block if opening balance is below minimum balance
        if (account.getBalance() < account.getMinimumBalance()) {
            account.setBlocked(true);
            account.setBlockedReason("Opening balance ₹" + account.getBalance()
                    + " is below the minimum balance of ₹" + account.getMinimumBalance()
                    + ". Account is blocked until sufficient funds are deposited.");
        }

        return repository.save(account);
    }

    // ─── Get All Accounts ────────────────────────────────────────────────────
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    // ─── Get Single Account ──────────────────────────────────────────────────
    public Account getAccount(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account Not Found"));
    }

    // ─── Deposit ─────────────────────────────────────────────────────────────
    public Account deposit(Long id, Double amount) {
        Account acc = getAccount(id);

        acc.setBalance(acc.getBalance() + amount);

        // Auto-unblock if balance is now >= minimum balance
        if (acc.getBlocked() && acc.getBalance() >= acc.getMinimumBalance()) {
            acc.setBlocked(false);
            acc.setBlockedReason(null);
        }

        return repository.save(acc);
    }

    // ─── Withdraw ────────────────────────────────────────────────────────────
    public Account withdraw(Long id, Double amount) {
        Account acc = getAccount(id);

        // ✅ Block check: reject all transactions on blocked accounts
        if (acc.getBlocked()) {
            throw new RuntimeException("Account is BLOCKED. Reason: " + acc.getBlockedReason()
                    + " — Please deposit funds to meet the minimum balance of ₹" + acc.getMinimumBalance() + ".");
        }

        if (acc.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance. Available: ₹" + acc.getBalance());
        }

        double newBalance = acc.getBalance() - amount;

        // ✅ Minimum Balance Check: block if withdrawal would drop below minimum
        if (newBalance < acc.getMinimumBalance()) {
            throw new RuntimeException(
                    "Withdrawal denied! This withdrawal would leave ₹" + String.format("%.2f", newBalance)
                    + ", which is below the minimum balance of ₹" + acc.getMinimumBalance()
                    + ". You can withdraw at most ₹" + String.format("%.2f", acc.getBalance() - acc.getMinimumBalance()) + ".");
        }

        acc.setBalance(newBalance);
        return repository.save(acc);
    }

    // ─── Update Minimum Balance ──────────────────────────────────────────────
    public Account setMinimumBalance(Long id, Double minimumBalance) {
        if (minimumBalance < 0) {
            throw new RuntimeException("Minimum balance cannot be negative.");
        }
        Account acc = getAccount(id);
        acc.setMinimumBalance(minimumBalance);

        // Re-evaluate blocked status based on new minimum balance
        if (acc.getBalance() < minimumBalance) {
            acc.setBlocked(true);
            acc.setBlockedReason("Balance ₹" + acc.getBalance()
                    + " is below the updated minimum balance of ₹" + minimumBalance + ".");
        } else {
            acc.setBlocked(false);
            acc.setBlockedReason(null);
        }

        return repository.save(acc);
    }

    // ─── Manual Block / Unblock ──────────────────────────────────────────────
    public Account blockAccount(Long id, String reason) {
        Account acc = getAccount(id);
        acc.setBlocked(true);
        acc.setBlockedReason(reason != null && !reason.isBlank() ? reason : "Manually blocked by administrator.");
        return repository.save(acc);
    }

    public Account unblockAccount(Long id) {
        Account acc = getAccount(id);
        // Only allow unblock if balance meets minimum balance
        if (acc.getBalance() < acc.getMinimumBalance()) {
            throw new RuntimeException(
                    "Cannot unblock: Current balance ₹" + acc.getBalance()
                    + " is still below the minimum balance of ₹" + acc.getMinimumBalance()
                    + ". Please deposit funds first.");
        }
        acc.setBlocked(false);
        acc.setBlockedReason(null);
        return repository.save(acc);
    }

    // ─── Delete Account ──────────────────────────────────────────────────────
    public void deleteAccount(Long id) {
        repository.deleteById(id);
    }
}