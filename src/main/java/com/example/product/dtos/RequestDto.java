package com.example.product.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private String title ;
    private int price ;
    private String email ;
    private String description ;
    private String image ;
    private String category;
}
