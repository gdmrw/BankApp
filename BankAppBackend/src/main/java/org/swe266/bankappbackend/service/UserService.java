package org.swe266.bankappbackend.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.swe266.bankappbackend.entity.User;
import org.swe266.bankappbackend.repository.UserRepository;


import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.swe266.bankappbackend.utils.ValidationUtils.*;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<?> registerUser(String username, String password, String balance, HttpSession session) {
        // validation
        if (!isValidPasswordOrUsername(username) || !isValidPasswordOrUsername(password)) { // bad code - not validate the username
            String errorMessage = "Invalid input. Please provide valid username and password.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        if (!isValidAmount(Double.parseDouble(balance))) {
            String errorMessage = "Invalid input. Please provide valid initial balance.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }


        if (userRepository.existsByUsername(username)) {
            String errorMessage = "Username exists, please change a name";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }


        User user = new User();
        user.setUsername(username);
        user.setBalance(Double.parseDouble(balance));

        // Hash the password before storing it
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        session.setAttribute("currentUser", username);

        // Save user using UserRepository to prevent SQL injection
        userRepository.save(user);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> logInUser(String username, String password, HttpSession session) {
        //validation
        if (!isValidPasswordOrUsername(username) || !isValidPasswordOrUsername(password)) {
            String errorMessage = "Invalid input. Please provide valid username and password.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        if (!userRepository.existsByUsername(username)) {
            String errorMessage = "User Not Found";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        // Check the hashed password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            String errorMessage = "Wrong Password";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        session.setAttribute("currentUser", username);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> deposit(String amount, HttpSession session) {
        //validation
        if (!isValidAmount(Double.parseDouble(amount))) {
            String errorMessage = "Invalid input. Please provide valid amount.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

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
        //validation
        if (!isValidAmount(Double.parseDouble(amount))) {
            String errorMessage = "Invalid input. Please provide valid amount.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

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
        if (userResult.isEmpty()) {
            String errorMessage = "User Not Login";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        Map<String, Object> response = new HashMap<>();
        response.put("balance", user.getBalance());
        return ResponseEntity.ok(response);
    }
}
