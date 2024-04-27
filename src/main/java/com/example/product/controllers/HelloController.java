package com.example.product.controllers;

import com.example.product.dtos.ProductResponseDto;
import com.example.product.dtos.RequestDto;
import com.example.product.models.Category;
import com.example.product.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
public class HelloController {
    private RestTemplate restTemplate ;
    public HelloController(RestTemplate restTemplate){
        this.restTemplate = restTemplate ;
    }

    @GetMapping("/Kaushal")
    public String getMessage(){

        return "Kaushal , Sr No 211 , Bhekrainagar , Fursungi , Pune 412308" ;
    }
    @RequestMapping("/dummy")
    public void dummyApi(){
        String answer =
            restTemplate.getForObject("http://user/hi", String.class);
        System.out.println(answer);
    }


}
