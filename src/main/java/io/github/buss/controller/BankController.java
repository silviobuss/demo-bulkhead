package io.github.buss.controller;

import io.github.buss.service.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bank")
public class BankController {

    private final Service bankService;

    public BankController(@Qualifier("bankService") Service bankService){
        this.bankService = bankService;
    }

    @PostMapping("/cashOut/{value}")
    public void cashOut(@PathVariable("value") double value) throws InterruptedException {
        bankService.cashOut(value);
    }

    @PostMapping("/deposit/{value}")
    public void deposit(@PathVariable("value") double value) throws InterruptedException {
        bankService.deposit(value);
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> balance(){
        return ResponseEntity.status(HttpStatus.OK).body(bankService.balance());
    }
}
