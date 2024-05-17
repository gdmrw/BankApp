package org.swe266.bankappbackend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swe266.bankappbackend.service.UserService;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password, @RequestParam String balance, HttpSession session) {
        return userService.registerUser(username, password, balance, session);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        return userService.logInUser(username, password, session);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam String amount,HttpSession session) {
        return userService.deposit(amount, session);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam String amount, HttpSession session) {
        return userService.withdraw(amount, session);
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(HttpSession session) {
        return userService.checkBalance(session);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect";
    }
}
