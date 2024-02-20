package com.fiore.springboottesting.controller;

import com.fiore.springboottesting.model.AuthenticationBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AuthenticationController {


    @GetMapping("basicauth")
    public ResponseEntity<AuthenticationBean> basicAuth(){
        log.info("logging...");
        return ResponseEntity.ok(new AuthenticationBean("Authenticated User"));
    }

}
