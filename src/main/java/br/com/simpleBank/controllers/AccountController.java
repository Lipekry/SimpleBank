package br.com.simpleBank.controllers;

import br.com.simpleBank.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Map;

@RestController
public class AccountController {

    @GetMapping("/balance")
    public ResponseEntity<Integer> getAccountBalance(@RequestParam String account_id) {
        try {
            return ResponseEntity.ok(AccountService.getAccountBalance(account_id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(0);
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetAccounts() {
        try {
            return ResponseEntity.ok(AccountService.resetAccounts());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/event")
    public ResponseEntity<?> executeEvents(@RequestBody Map<String, Object> request) {
        try {
            return ResponseEntity.status(201).body(AccountService.handleEvent(request));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }
}
