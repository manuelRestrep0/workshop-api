package com.manuel.validator.controller;

import com.manuel.validator.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class ValidatorController {
    private ValidatorService validatorService;

    @Autowired
    public void ValidatorController(ValidatorService validatorService){
        this.validatorService = validatorService;
    }

    @PostMapping("/validar")
    public boolean validarLinea(@RequestBody String linea){
        return this.validatorService.validar(linea);

    }
}
