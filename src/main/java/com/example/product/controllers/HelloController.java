package com.example.product.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/Kaushal")
    public String getMessage(){
        return "Kaushal , Sr No 211 , Bhekrainagar , Fursungi , Pune 412308" ;
    }
}
