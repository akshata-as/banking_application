package com.example.demo;
import jakarta.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountHolderName;
    private String accountNumber;
    private Double balance;

    // ✅ Minimum Balance Feature
    private Double minimumBalance = 500.0;  // Default minimum balance ₹500

    // ✅ Account Blocking Feature
    private Boolean blocked = false;
    private String blockedReason;

    public Account() {
    }

    public Account(Long id, String accountHolderName, String accountNumber, Double balance) {
        super();
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.minimumBalance = 500.0;
        this.blocked = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountHolderName() { return accountHolderName; }
    public void setAccountHolderName(String accountHolderName) { this.accountHolderName = accountHolderName; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public Double getMinimumBalance() { return minimumBalance != null ? minimumBalance : 500.0; }
    public void setMinimumBalance(Double minimumBalance) { this.minimumBalance = minimumBalance; }

    public Boolean getBlocked() { return blocked != null ? blocked : false; }
    public void setBlocked(Boolean blocked) { this.blocked = blocked; }

    public String getBlockedReason() { return blockedReason; }
    public void setBlockedReason(String blockedReason) { this.blockedReason = blockedReason; }
}