package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    // ✅ Login — verify credentials, auto-create account if missing, return safe user
    public User login(String username, String password) {
        // Fetch user from DB
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify password
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        // Auto-create bank account for regular users who have none
        if (user.getAccountId() == null && "user".equalsIgnoreCase(user.getRole())) {
            Account account = new Account();
            account.setAccountHolderName(user.getFullName());
            account.setAccountNumber("ACC-" + System.currentTimeMillis());
            account.setBalance(0.0);
            Account savedAccount = accountRepository.save(account);

            // ✅ Update accountId on the SAME object (password still intact here)
            user.setAccountId(savedAccount.getId());
            userRepository.save(user); // safe — password is NOT null yet
        }

        // ✅ Only null the password AFTER saving — safe for DB, safe for frontend
        user.setPassword(null);
        return user;
    }

    // ✅ Register — auto-creates a bank account for role=user
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        if ("admin".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Cannot self-register as admin");
        }

        // Auto-create a bank account for regular users
        if ("user".equalsIgnoreCase(user.getRole())) {
            Account account = new Account();
            account.setAccountHolderName(user.getFullName());
            account.setAccountNumber("ACC-" + System.currentTimeMillis());
            account.setBalance(0.0);
            Account saved = accountRepository.save(account);
            user.setAccountId(saved.getId());
        }

        User saved = userRepository.save(user);
        saved.setPassword(null); // mask after saving
        return saved;
    }

    // ✅ Get all users (password masked)
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(u -> u.setPassword(null));
        return users;
    }

    // ✅ Get user by ID
    public User getUser(Long id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        u.setPassword(null);
        return u;
    }

    // ✅ Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}