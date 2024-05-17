package org.swe266.bankappbackend.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.swe266.bankappbackend.entity.User;
import org.swe266.bankappbackend.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<?> registerUser(String username, String password, String balance, HttpSession session) {

        if (userRepository.existsByUsername(username)) {
            String errorMessage = "Username exists, please change a name";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setBalance(Double.parseDouble(balance));
        user.setPassword(encodedPassword);
        session.setAttribute("currentUser", username);
        User savedUser = userRepository.save(user);
        savedUser.setPassword(null);
        return ResponseEntity.ok(savedUser);
    }

    public ResponseEntity<?> logInUser(String username, String password, HttpSession session) {
        if (!userRepository.existsByUsername(username)) {
            String errorMessage = "User Not Found";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
        if (!passwordMatches) {
            String errorMessage = "Wrong Password";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }

        session.setAttribute("currentUser", username);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> deposit(String amount, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        Optional<User> userResult = userRepository.findByUsername(username);
        if(userResult.isEmpty()) {
            String errorMessage = "User Not Login";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        user.setBalance(user.getBalance() + Double.parseDouble(amount));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> withdraw(String amount, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        Optional<User> userResult = userRepository.findByUsername(username);
        if(userResult.isEmpty()) {
            String errorMessage = "User Not Login";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        if (Double.parseDouble(amount) > user.getBalance()) {
            String errorMessage = "Insufficient balance for user '" + username + "'.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        user.setBalance(user.getBalance() - Double.parseDouble(amount));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> checkBalance(HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        Optional<User> userResult = userRepository.findByUsername(username);
        if(userResult.isEmpty()) {
            String errorMessage = "User Not Login";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        return ResponseEntity.ok(user);
    }
}
