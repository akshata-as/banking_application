package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

// ✅ Seeds default admin, manager, and sample users on first startup
@Component
public class UserDataseeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) {

        // Only seed if no users exist yet
        if (userRepository.count() == 0) {

            // Create sample bank accounts using no-arg constructor + setters
            Account acc1 = new Account();
            acc1.setAccountHolderName("Ravi Kumar");
            acc1.setAccountNumber("ACC-1001");
            acc1.setBalance(25000.0);
            acc1 = accountRepository.save(acc1);

            Account acc2 = new Account();
            acc2.setAccountHolderName("Priya Singh");
            acc2.setAccountNumber("ACC-1002");
            acc2.setBalance(12500.0);
            acc2 = accountRepository.save(acc2);

            Account acc3 = new Account();
            acc3.setAccountHolderName("Amit Sharma");
            acc3.setAccountNumber("ACC-1003");
            acc3.setBalance(8000.0);
            acc3 = accountRepository.save(acc3);

            // Admin — full access, no linked account
            userRepository.save(new User("admin",   "admin123",  "admin",   "Admin",       null));

            // Manager — view all accounts, no delete
            userRepository.save(new User("manager", "vault2024", "manager", "Manager",     null));

            // Regular users — each linked to their own bank account
            userRepository.save(new User("user1",   "user123",   "user",    "Ravi Kumar",  acc1.getId()));
            userRepository.save(new User("user2",   "user456",   "user",    "Priya Singh", acc2.getId()));
            userRepository.save(new User("user3",   "user789",   "user",    "Amit Sharma", acc3.getId()));

            System.out.println("✅ Default users and accounts seeded successfully.");
        }
    }
}
