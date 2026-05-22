package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin
public class AccountController {

    private AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    // ─── Create Account ──────────────────────────────────────────────────────
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return service.createAccount(account);
    }

    // ─── Get All Accounts ────────────────────────────────────────────────────
    @GetMapping
    public List<Account> getAllAccounts() {
        return service.getAllAccounts();
    }

    // ─── Get Account By ID ───────────────────────────────────────────────────
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return service.getAccount(id);
    }

    // ─── Deposit Money ───────────────────────────────────────────────────────
    @PutMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id,
                           @RequestParam Double amount) {
        return service.deposit(id, amount);
    }

    // ─── Withdraw Money ──────────────────────────────────────────────────────
    @PutMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id,
                            @RequestParam Double amount) {
        return service.withdraw(id, amount);
    }

    // ─── Set / Update Minimum Balance ────────────────────────────────────────
    // PUT /api/accounts/{id}/minimum-balance?amount=1000
    @PutMapping("/{id}/minimum-balance")
    public Account setMinimumBalance(@PathVariable Long id,
                                     @RequestParam Double amount) {
        return service.setMinimumBalance(id, amount);
    }

    // ─── Block Account ───────────────────────────────────────────────────────
    // PUT /api/accounts/{id}/block?reason=Suspicious+Activity
    @PutMapping("/{id}/block")
    public Account blockAccount(@PathVariable Long id,
                                @RequestParam(required = false) String reason) {
        return service.blockAccount(id, reason);
    }

    // ─── Unblock Account ─────────────────────────────────────────────────────
    // PUT /api/accounts/{id}/unblock
    @PutMapping("/{id}/unblock")
    public Account unblockAccount(@PathVariable Long id) {
        return service.unblockAccount(id);
    }

    // ─── Delete Account ──────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return "Account Deleted Successfully";
    }
}